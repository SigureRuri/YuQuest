package com.github.sigureruri.yuquest.quest.mission

import com.github.sigureruri.yuquest.quest.QuestMember

interface MemberRelatedEvent {
    val members: Set<QuestMember>
}