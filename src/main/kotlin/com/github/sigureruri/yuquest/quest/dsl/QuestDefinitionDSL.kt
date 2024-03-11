package com.github.sigureruri.yuquest.quest.dsl

import com.github.sigureruri.yuquest.quest.Quest
import com.github.sigureruri.yuquest.quest.QuestDefinition
import com.github.sigureruri.yuquest.util.YuId
import net.kyori.adventure.text.Component

class QuestDefinitionDSL(val id: YuId) {
    var displayName = Component.empty()
    var description = listOf<Component>()

    private val missionDefinitionDsl = MissionDefinitionsDSL()

    private var start: Quest.() -> Unit = {}
    private var end: Quest.() -> Unit = {}
    private var complete: Quest.() -> Unit = {}

    fun missions(block: MissionDefinitionsDSL.() -> Unit) {
        missionDefinitionDsl.apply(block)
    }

    fun start(block: Quest.() -> Unit) {
        start = block
    }

    fun end(block: Quest.() -> Unit) {
        end = block
    }

    fun complete(block: Quest.() -> Unit) {
        complete = block
    }


    internal fun toQuestDefinition() = QuestDefinition(
        id,
        displayName,
        description,
        missionDefinitionDsl.toMissionDefinitions(),
        start,
        end,
        complete
    )
}