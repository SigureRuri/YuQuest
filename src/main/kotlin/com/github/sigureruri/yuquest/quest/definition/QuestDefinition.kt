package com.github.sigureruri.yuquest.quest.definition

import com.github.sigureruri.yuquest.data.identified.Identified
import com.github.sigureruri.yuquest.quest.Quest
import com.github.sigureruri.yuquest.util.YuId
import net.kyori.adventure.text.Component

data class QuestDefinition(
    override val id: YuId,
    val displayName: Component,
    val description: List<Component>,
    val missionDefinitions: MissionDefinitions,
    val start: (Quest) -> Unit,
    val end: (Quest) -> Unit,
    val complete: (Quest) -> Unit,
) : Identified<YuId>()