package com.github.sigureruri.yuquest.quest.dsl

import com.github.sigureruri.yuquest.quest.Quest
import com.github.sigureruri.yuquest.quest.QuestMember
import com.github.sigureruri.yuquest.quest.definition.QuestDefinition
import com.github.sigureruri.yuquest.util.YuId
import net.kyori.adventure.text.Component

class QuestDefinitionDSL(val id: YuId) {
    var displayName = Component.empty()
    var description = listOf<Component>()

    private val missionDefinitionDsl = MissionDefinitionsDSL()

    private var initializeOnce: Quest.() -> Unit = {}
    private var initializeForEachMember: QuestMember.() -> Unit = {}
    private var finalizeOnce: Quest.() -> Unit = {}
    private var finalizeForEachMember: QuestMember.() -> Unit = {}
    private var completeOnce: Quest.() -> Unit = {}
    private var completeForEachMember: QuestMember.() -> Unit = {}

    fun missions(block: MissionDefinitionsDSL.() -> Unit) {
        missionDefinitionDsl.apply(block)
    }

    fun initializeOnce(block: Quest.() -> Unit) {
        initializeOnce = block
    }

    fun initializeForEachMember(block: QuestMember.() -> Unit) {
        initializeForEachMember = block
    }

    fun finalizeOnce(block: Quest.() -> Unit) {
        finalizeOnce = block
    }

    fun finalizeForEachMember(block: QuestMember.() -> Unit) {
        finalizeForEachMember = block
    }

    fun completeOnce(block: Quest.() -> Unit) {
        completeOnce = block
    }

    fun completeForEachMember(block: QuestMember.() -> Unit) {
        completeForEachMember = block
    }


    internal fun toQuestDefinition() = QuestDefinition(
        id,
        displayName,
        description,
        missionDefinitionDsl.toMissionDefinitions(),
        initializeOnce,
        initializeForEachMember,
        finalizeOnce,
        finalizeForEachMember,
        completeOnce,
        completeForEachMember
    )
}