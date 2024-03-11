package com.github.sigureruri.yuquest.quest.dsl

import com.github.sigureruri.yuquest.quest.mission.MemberRelatedEvent
import com.github.sigureruri.yuquest.quest.mission.Mission
import com.github.sigureruri.yuquest.quest.mission.definition.DefaultMissionEffect

class DefaultMissionEffectDSL {
    private var filter: MemberRelatedEvent.() -> Boolean = { true }
    private var start: Mission<*>.() -> Unit = { }
    private var end: Mission<*>.() -> Unit = { }
    private var complete: Mission<*>.() -> Unit = { }
    private var fire: Mission<*>.() -> Unit = { }

    fun filter(block: MemberRelatedEvent.() -> Boolean) {
        filter = block
    }

    fun start(block: Mission<*>.() -> Unit) {
        start = block
    }

    fun end(block: Mission<*>.() -> Unit) {
        end = block
    }

    fun complete(block: Mission<*>.() -> Unit) {
        complete = block
    }

    fun fire(block: Mission<*>.() -> Unit) {
        fire = block
    }


    internal fun toDefaultMissionEffect() = DefaultMissionEffect(
        { _, event -> filter(event) },
        { _, mission -> start(mission) },
        { _, mission -> end(mission) },
        { _, mission -> complete(mission) },
        { _, mission -> fire(mission) }
    )
}