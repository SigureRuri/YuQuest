package com.github.sigureruri.yuquest.quest.missiontype

import com.github.sigureruri.yuquest.data.identified.IdentifiedDataRepository
import com.github.sigureruri.yuquest.quest.QuestMember
import java.util.UUID

interface MemberRelatedEvent {
    val members: IdentifiedDataRepository<UUID, QuestMember>
}