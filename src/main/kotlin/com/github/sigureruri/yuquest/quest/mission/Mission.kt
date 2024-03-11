package com.github.sigureruri.yuquest.quest.mission

import com.github.sigureruri.yuquest.data.identified.Identified
import com.github.sigureruri.yuquest.quest.Quest
import com.github.sigureruri.yuquest.quest.mission.definition.DefaultMissionEffect
import com.github.sigureruri.yuquest.quest.mission.definition.MissionDefinition
import com.github.sigureruri.yuquest.quest.mission.dependency.MissionDependency
import com.github.sigureruri.yuquest.quest.mission.dependency.MissionDependencyInterpreter
import com.github.sigureruri.yuquest.util.YuId
import kotlin.math.max

class Mission<T : MemberRelatedEvent> @Deprecated("Internal only") internal constructor(
    val quest: Quest,
    private val missionDefinition: MissionDefinition<T>,
    private val defaultEffect: DefaultMissionEffect
) : Identified<YuId>() {
    override val id: YuId = missionDefinition.id

    val type: MissionType<T> = missionDefinition.type

    val dependency: MissionDependency = missionDefinition.dependency

    val requiredCountToFinish: Int = missionDefinition.requiredCountToFinish

    var status: Status = Status.NOT_STARTED_YET
        private set

    // positive int instead of using UInt
    var count = 0
        private set(value) {
            field = max(0, value)
        }

    fun filter(context: T): Boolean {
        return defaultEffect.filter(this, context) && missionDefinition.filter(this, context)
    }

    fun start() {
        requireNotStarted()
        status = Status.STARTED

        missionDefinition.start(quest, this)
        defaultEffect.start(quest ,this)
    }

    fun end() {
        requireStarted()
        status = Status.FORCIBLY_ENDED

        missionDefinition.end(quest, this)
        defaultEffect.end(quest, this)
    }

    fun complete() {
        requireStarted()
        requireFulfillingRequiredCount()
        status = Status.COMPLETED

        missionDefinition.end(quest, this)
        missionDefinition.complete(quest, this)
        defaultEffect.end(quest, this)
        defaultEffect.complete(quest, this)
    }

    fun fire() {
        requireStarted()

        val result = missionDefinition.fire(quest, this) as? EventResult.Success ?: return
        defaultEffect.fire(quest, this)

        count += result.count

        if (count >= requiredCountToFinish) {
            complete()

            quest.missions.let { missions ->
                if (missions.all { it.status.isEnded() }) {
                    quest.complete()
                } else {
                    missions
                        .filter { it.status == Status.NOT_STARTED_YET }
                        .forEach {
                            if (MissionDependencyInterpreter.fulfillConditions(quest, it.dependency)) {
                                it.start()
                            }
                        }
                }
            }
        }
    }

    private fun requireNotStarted() = require(status == Status.NOT_STARTED_YET) { "${toString()} should not be started" }

    private fun requireStarted() = require(status == Status.STARTED) { "${toString()} should be started" }

    private fun requireFulfillingRequiredCount() = require(count >= requiredCountToFinish)

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

    sealed class EventResult {
        data object Failure : EventResult()
        data class Success(val count: Int) : EventResult()
    }
}