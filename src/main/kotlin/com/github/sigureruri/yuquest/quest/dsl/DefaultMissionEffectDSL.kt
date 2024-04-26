package com.github.sigureruri.yuquest.quest.dsl

import com.github.sigureruri.yuquest.quest.Mission
import com.github.sigureruri.yuquest.quest.QuestMember
import com.github.sigureruri.yuquest.quest.definition.DefaultMissionEffect
import com.github.sigureruri.yuquest.quest.missiontype.MemberRelatedEvent

class DefaultMissionEffectDSL {
    private var filter: MemberRelatedEvent.() -> Boolean = { true }
    private var initializeOnce: Mission<*>.() -> Unit = { }
    private var initializeForEachMember: QuestMember.() -> Unit = { }
    private var finalizeOnce: Mission<*>.() -> Unit = { }
    private var finalizeForEachMember: QuestMember.() -> Unit = { }
    private var completeOnce: Mission<*>.() -> Unit = { }
    private var completeForEachMember: QuestMember.() -> Unit = { }
    private var fire: Mission<*>.() -> Unit = { }

    fun filter(block: MemberRelatedEvent.() -> Boolean) {
        filter = block
    }

    fun initializeOnce(block: Mission<*>.() -> Unit) {
        initializeOnce = block
    }

    fun initializeForEachMember(block: QuestMember.() -> Unit) {
        initializeForEachMember = block
    }

    fun finalizeOnce(block: Mission<*>.() -> Unit) {
        finalizeOnce = block
    }

    fun finalizeForEachMember(block: QuestMember.() -> Unit) {
        finalizeForEachMember = block
    }

    fun completeOnce(block: Mission<*>.() -> Unit) {
        completeOnce = block
    }

    fun completeForEachMember(block: QuestMember.() -> Unit) {
        completeForEachMember = block
    }

    fun fire(block: Mission<*>.() -> Unit) {
        fire = block
    }


    internal fun toDefaultMissionEffect() = DefaultMissionEffect(
        { _, event -> filter(event) },
        { mission -> initializeOnce(mission) },
        { member -> initializeForEachMember(member) },
        { mission -> finalizeOnce(mission) },
        { member -> finalizeForEachMember(member) },
        { mission -> completeOnce(mission) },
        { member -> completeForEachMember(member) },
        { mission, event -> fire(mission) }
    )
}