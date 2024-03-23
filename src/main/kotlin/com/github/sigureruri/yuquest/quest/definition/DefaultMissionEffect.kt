package com.github.sigureruri.yuquest.quest.definition

import com.github.sigureruri.yuquest.quest.Mission
import com.github.sigureruri.yuquest.quest.Quest
import com.github.sigureruri.yuquest.quest.missiontype.MemberRelatedEvent

data class DefaultMissionEffect(
    val filter: (Mission<*>, MemberRelatedEvent) -> Boolean = { _, _ -> true },
    val start: (Quest, Mission<*>) -> Unit = { _, _ -> },
    val end: (Quest, Mission<*>) -> Unit = { _, _ -> },
    val complete: (Quest, Mission<*>) -> Unit = { _, _ -> },
    val fire: (Quest, Mission<*>) -> Unit = { _, _ -> }
)