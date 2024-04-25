package com.github.sigureruri.yuquest.quest.definition

import com.github.sigureruri.yuquest.quest.Mission
import com.github.sigureruri.yuquest.quest.QuestMember
import com.github.sigureruri.yuquest.quest.missiontype.MemberRelatedEvent

data class DefaultMissionEffect(
    val filter: (Mission<*>, MemberRelatedEvent) -> Boolean = { _, _ -> true },
    val initializeOnce: (Mission<*>) -> Unit = { },
    val initializeForEachMember: (QuestMember) -> Unit = { },
    val finalizeOnce: (Mission<*>) -> Unit = { },
    val finalizeForEachMember: (QuestMember) -> Unit = { },
    val completeOnce: (Mission<*>) -> Unit = { },
    val completeForEachMember: (QuestMember) -> Unit = { },
    val fire: (Mission<*>, MemberRelatedEvent) -> Unit = { _, _ -> }
)