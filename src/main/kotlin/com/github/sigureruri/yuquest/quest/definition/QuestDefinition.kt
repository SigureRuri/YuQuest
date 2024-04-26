package com.github.sigureruri.yuquest.quest.definition

import com.github.sigureruri.yuquest.data.identified.Identified
import com.github.sigureruri.yuquest.quest.Quest
import com.github.sigureruri.yuquest.quest.QuestMember
import com.github.sigureruri.yuquest.util.YuId
import net.kyori.adventure.text.Component

data class QuestDefinition(
    override val id: YuId,
    val displayName: Component,
    val description: List<Component>,
    val missionDefinitions: MissionDefinitions,
    val initializeOnce: (Quest) -> Unit,
    val initializeForEachMember: (QuestMember) -> Unit,
    val finalizeOnce: (Quest) -> Unit,
    val finalizeForEachMember: (QuestMember) -> Unit,
    val completeOnce: (Quest) -> Unit,
    val completeForEachMember: (QuestMember) -> Unit
) : Identified<YuId>()