package com.github.sigureruri.yuquest.quest

import com.github.sigureruri.yuquest.data.identified.Identified
import com.github.sigureruri.yuquest.data.identified.IdentifiedDataRepository
import com.github.sigureruri.yuquest.quest.definition.QuestDefinition
import com.github.sigureruri.yuquest.quest.util.MissionDependencyInterpreter
import com.github.sigureruri.yuquest.util.YuId
import java.util.*

class Quest @Deprecated("Internal only") internal constructor(
    override val id: UUID,
    private val tracker: QuestTracker,
    val definition: QuestDefinition,
    status: Status = Status.NOT_STARTED_YET,
    private val membersRepository: IdentifiedDataRepository<UUID, QuestMember> = IdentifiedDataRepository(),
    defaultMissionValues: Map<YuId, MissionDefaultValue> = mapOf()
) : Identified<UUID>() {
    constructor(tracker: QuestTracker, definition: QuestDefinition) : this(UUID.randomUUID(), tracker, definition)

    val members: Set<QuestMember>
        get() = membersRepository.values

    var status = status
        private set

    private val missionsRepository: IdentifiedDataRepository<YuId, Mission<*>> =
        IdentifiedDataRepository<YuId, Mission<*>>().apply {
            definition.missionDefinitions.definitions
                .map {
                    if (defaultMissionValues.contains(it.id)) {
                        val defaultValue = defaultMissionValues[it.id]!!
                        Mission(this@Quest, it, definition.missionDefinitions.defaultEffect, defaultValue.status, defaultValue.currentCount)
                    } else {
                        Mission(this@Quest, it, definition.missionDefinitions.defaultEffect)
                    }
                }.forEach { put(it) }
        }

    val missions: Set<Mission<*>>
        get() = missionsRepository.values

    fun requestToStartTracking(): Boolean {
        if (tracker.trackingQuests.contains(this)) {
            return false
        } else {
            tracker.startTracking(this)
            return true
        }
    }

    fun start() {
        requireNotStarted()
        status = Status.STARTED

        missionsRepository.values.forEach {
            if (MissionDependencyInterpreter.fulfillConditions(this, it.dependency)) {
                it.start()
            }
        }

        tracker.startTracking(this)

        definition.start(this)
    }

    fun end() {
        requireStarted()
        status = Status.FORCIBLY_ENDED

        missionsRepository.values
            .filter { it.status == Mission.Status.STARTED }
            .forEach {
                it.end()
            }

        tracker.endTracking(this)

        definition.end(this)
    }

    fun complete() {
        requireStarted()
        requireAllMissionsAreFinished()
        status = Status.COMPLETED

        tracker.endTracking(this)

        definition.complete(this)
    }

    fun addMember(member: QuestMember) {
        membersRepository.put(member)
    }

    fun removeMember(member: QuestMember) {
        membersRepository.remove(member)
    }

    private fun requireNotStarted() = require(status == Status.NOT_STARTED_YET) { "${toString()} should not be started" }

    private fun requireStarted() = require(status == Status.STARTED) { "${toString()} should be started" }

    private fun requireAllMissionsAreFinished() =
        require(missionsRepository.values.all { it.status.isEnded() })

    enum class Status {
        NOT_STARTED_YET,
        STARTED,
        FORCIBLY_ENDED,
        COMPLETED;

        fun isEnded() = when (this) {
            NOT_STARTED_YET, STARTED -> false
            FORCIBLY_ENDED, COMPLETED -> true
        }
    }

    internal data class MissionDefaultValue(
        val status: Mission.Status,
        val currentCount: Int
    )
}