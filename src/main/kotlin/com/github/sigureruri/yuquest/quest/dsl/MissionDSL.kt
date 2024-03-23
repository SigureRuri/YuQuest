package com.github.sigureruri.yuquest.quest.dsl

import com.github.sigureruri.yuquest.quest.Mission
import com.github.sigureruri.yuquest.quest.definition.MissionDefinition
import com.github.sigureruri.yuquest.quest.definition.MissionDependency
import com.github.sigureruri.yuquest.quest.definition.asMissionDependency
import com.github.sigureruri.yuquest.quest.missiontype.MemberRelatedEvent
import com.github.sigureruri.yuquest.quest.missiontype.MissionType
import com.github.sigureruri.yuquest.util.YuId

class MissionDSL<T : MemberRelatedEvent>(val id: YuId, val type: MissionType<T>) {
    var requiredCountToFinish = 1
    private var dependency: MissionDependency = MissionDependency.Nothing
    private var filter: T.() -> Boolean = { true }
    private var start: Mission<T>.() -> Unit = { }
    private var end: Mission<T>.() -> Unit = { }
    private var complete: Mission<T>.() -> Unit = { }
    private var fire: Mission<T>.() -> Mission.EventResult = { Mission.EventResult.Success(1) }


    fun asRoot() {
        dependency = MissionDependency.Nothing
    }

    infix fun dependsOn(that: MissionDependency) {
        dependency = that
    }

    infix fun dependsOn(that: YuId) {
        dependency = that.asMissionDependency()
    }

    fun filter(block: T.() -> Boolean) {
        filter = block
    }

    fun start(block: Mission<T>.() -> Unit) {
        start = block
    }

    fun end(block: Mission<T>.() -> Unit) {
        end = block
    }

    fun complete(block: Mission<T>.() -> Unit) {
        complete = block
    }

    fun fire(block: Mission<T>.() -> Mission.EventResult) {
        fire = block
    }

    infix fun MissionDependency.and(that: MissionDependency): MissionDependency = MissionDependency.And(this, that)
    infix fun MissionDependency.and(that: YuId): MissionDependency = MissionDependency.And(this, that.asMissionDependency())
    infix fun YuId.and(that: YuId): MissionDependency = MissionDependency.And(asMissionDependency(), that.asMissionDependency())
    infix fun MissionDependency.or(that: MissionDependency): MissionDependency = MissionDependency.Or(this, that)
    infix fun MissionDependency.or(that: YuId): MissionDependency = MissionDependency.Or(this, that.asMissionDependency())
    infix fun YuId.or(that: YuId): MissionDependency = MissionDependency.Or(asMissionDependency(), that.asMissionDependency())


    internal fun toMissionDefinition() = MissionDefinition(
        id,
        type,
        dependency,
        requiredCountToFinish,
        { _, t -> filter(t) },
        { _, mission -> start(mission) },
        { _, mission -> end(mission) },
        { _, mission -> complete(mission) },
        { _, mission -> fire(mission) },
    )
}