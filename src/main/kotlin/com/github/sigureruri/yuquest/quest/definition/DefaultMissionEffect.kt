package com.github.sigureruri.yuquest.quest.definition

import com.github.sigureruri.yuquest.quest.Mission
import com.github.sigureruri.yuquest.quest.QuestMember
import com.github.sigureruri.yuquest.quest.missiontype.MemberRelatedEvent

data class DefaultMissionEffect(
    val filter: (Mission<*>, MemberRelatedEvent) -> Boolean = { _, _ -> true },
    val initializeOnce: (Mission<*>) -> Unit = { },
    val initializeForEachMember: (Mission<*>, QuestMember) -> Unit = { _, _ -> },
    val finalizeOnce: (Mission<*>) -> Unit = { },
    val finalizeForEachMember: (Mission<*>, QuestMember) -> Unit = { _, _ -> },
    val completeOnce: (Mission<*>) -> Unit = { },
    val completeForEachMember: (Mission<*>, QuestMember) -> Unit = { _, _ -> },
    val fire: (Mission<*>, MemberRelatedEvent) -> Unit = { _, _ -> }
)