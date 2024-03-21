package com.github.sigureruri.yuquest.quest.missiontype

import com.github.sigureruri.yuquest.quest.QuestMember

interface MemberRelatedEvent {
    val members: Set<QuestMember>
}