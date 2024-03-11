package com.github.sigureruri.yuquest.quest

import com.github.sigureruri.yuquest.data.identified.Identified
import com.github.sigureruri.yuquest.data.identified.IdentifiedDataRepository
import com.github.sigureruri.yuquest.quest.mission.Mission
import com.github.sigureruri.yuquest.quest.mission.dependency.MissionDependencyInterpreter
import com.github.sigureruri.yuquest.util.YuId
import java.util.*

class Quest @Deprecated("Internal only") internal constructor(
    override val id: UUID,
    private val tracker: QuestTracker,
    private val questDefinition: QuestDefinition,
) : Identified<UUID>() {
    constructor(tracker: QuestTracker, definition: QuestDefinition) : this(UUID.randomUUID(), tracker, definition)

    private val membersRepository: IdentifiedDataRepository<UUID, QuestMember> = IdentifiedDataRepository()

    val members: Set<QuestMember>
        get() = membersRepository.values

    var status = Status.NOT_STARTED_YET
        private set

    private val missionsRepository: IdentifiedDataRepository<YuId, Mission<*>> =
        IdentifiedDataRepository<YuId, Mission<*>>().apply {
            questDefinition.missionDefinitions.definitions
                .map { Mission(this@Quest, it, questDefinition.missionDefinitions.defaultEffect) }
                .forEach { put(it) }
        }

    val missions: Set<Mission<*>>
        get() = missionsRepository.values

    fun start() {
        requireNotStarted()
        status = Status.STARTED

        missionsRepository.values.forEach {
            if (MissionDependencyInterpreter.fulfillConditions(this, it.dependency)) {
                it.start()
            }
        }

        tracker.startTracking(this)

        questDefinition.start(this)
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

        questDefinition.end(this)
    }

    fun complete() {
        requireStarted()
        requireAllMissionsAreFinished()
        status = Status.COMPLETED

        tracker.endTracking(this)

        questDefinition.complete(this)
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
}