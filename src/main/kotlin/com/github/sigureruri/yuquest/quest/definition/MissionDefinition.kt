package com.github.sigureruri.yuquest.quest.definition

import com.github.sigureruri.yuquest.data.identified.Identified
import com.github.sigureruri.yuquest.quest.Quest
import com.github.sigureruri.yuquest.quest.Mission
import com.github.sigureruri.yuquest.quest.missiontype.MemberRelatedEvent
import com.github.sigureruri.yuquest.quest.missiontype.MissionType
import com.github.sigureruri.yuquest.util.YuId

data class MissionDefinition<T : MemberRelatedEvent>(
    override val id: YuId,
    val type: MissionType<T>,
    val dependency: MissionDependency,
    val requiredCountToFinish: Int,
    val filter: (Mission<T>, T) -> Boolean,
    val start: (Quest, Mission<T>) -> Unit,
    val end: (Quest, Mission<T>) -> Unit,
    val complete: (Quest, Mission<T>) -> Unit,
    val fire: (Quest, Mission<T>) -> Mission.EventResult
) : Identified<YuId>()