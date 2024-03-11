package com.github.sigureruri.yuquest.quest.mission.definition

import com.github.sigureruri.yuquest.data.identified.Identified
import com.github.sigureruri.yuquest.quest.Quest
import com.github.sigureruri.yuquest.quest.mission.MemberRelatedEvent
import com.github.sigureruri.yuquest.quest.mission.Mission
import com.github.sigureruri.yuquest.quest.mission.MissionType
import com.github.sigureruri.yuquest.quest.mission.dependency.MissionDependency
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