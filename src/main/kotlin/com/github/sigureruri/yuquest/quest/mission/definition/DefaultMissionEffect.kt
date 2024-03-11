package com.github.sigureruri.yuquest.quest.mission.definition

import com.github.sigureruri.yuquest.quest.Quest
import com.github.sigureruri.yuquest.quest.mission.MemberRelatedEvent
import com.github.sigureruri.yuquest.quest.mission.Mission

data class DefaultMissionEffect(
    val filter: (Mission<*>, MemberRelatedEvent) -> Boolean = { _, _ -> true },
    val start: (Quest, Mission<*>) -> Unit = { _, _ -> },
    val end: (Quest, Mission<*>) -> Unit = { _, _ -> },
    val complete: (Quest, Mission<*>) -> Unit = { _, _ -> },
    val fire: (Quest, Mission<*>) -> Unit = { _, _ -> }
)