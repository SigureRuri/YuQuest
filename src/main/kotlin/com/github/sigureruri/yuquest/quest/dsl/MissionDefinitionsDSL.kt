package com.github.sigureruri.yuquest.quest.dsl

import com.github.sigureruri.yuquest.quest.missiontype.MemberRelatedEvent
import com.github.sigureruri.yuquest.quest.missiontype.MissionType
import com.github.sigureruri.yuquest.quest.definition.MissionDefinitions
import com.github.sigureruri.yuquest.util.YuId

class MissionDefinitionsDSL {
    private var defaultEffectDsl = DefaultMissionEffectDSL()
    private val missions = mutableListOf<MissionDSL<*>>()

    fun <T : MemberRelatedEvent> mission(id: YuId, missionType: MissionType<T>, block: MissionDSL<T>.() -> Unit) {
        missions.add(MissionDSL(id, missionType).apply(block))
    }

    fun default(block: DefaultMissionEffectDSL.() -> Unit) {
        defaultEffectDsl = DefaultMissionEffectDSL().apply(block)
    }


    internal fun toMissionDefinitions() = MissionDefinitions().apply {
        withDefaultEffect(defaultEffectDsl.toDefaultMissionEffect())
        missions.map { it.toMissionDefinition() }.forEach { add(it) }
    }
}