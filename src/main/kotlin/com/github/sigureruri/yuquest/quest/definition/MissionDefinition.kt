package com.github.sigureruri.yuquest.quest.definition

import com.github.sigureruri.yuquest.data.identified.Identified
import com.github.sigureruri.yuquest.quest.Mission
import com.github.sigureruri.yuquest.quest.QuestMember
import com.github.sigureruri.yuquest.quest.missiontype.MemberRelatedEvent
import com.github.sigureruri.yuquest.quest.missiontype.MissionType
import com.github.sigureruri.yuquest.util.YuId

data class MissionDefinition<T : MemberRelatedEvent>(
    override val id: YuId,
    val type: MissionType<T>,
    val dependency: MissionDependency,
    val requiredCountToFinish: Int,
    val filter: (Mission<T>, T) -> Boolean,
    val initializeOnce: (Mission<T>) -> Unit,
    val initializeForEachMember: (QuestMember) -> Unit,
    val finalizeOnce: (Mission<T>) -> Unit,
    val finalizeForEachMember: (QuestMember) -> Unit,
    val completeOnce: (Mission<T>) -> Unit,
    val completeForEachMember: (QuestMember) -> Unit,
    val fire: (Mission<T>, MemberRelatedEvent) -> Mission.EventResult
) : Identified<YuId>()