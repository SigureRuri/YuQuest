package com.github.sigureruri.yuquest.util

import com.github.sigureruri.yuquest.YuQuest
import com.github.sigureruri.yuquest.playerdata.local.YuPlayerData
import com.github.sigureruri.yuquest.quest.QuestMember
import org.bukkit.Bukkit
import org.bukkit.entity.Player

inline fun QuestMember.toYuPlayerData(): YuPlayerData? {
    return YuQuest.INSTANCE.playerDataAccessor.getFromLocalRepository(id)
}

inline fun QuestMember.existsAsYuPlayerData(): Boolean {
    return YuQuest.INSTANCE.playerDataAccessor.getFromLocalRepository(id) != null
}

inline fun QuestMember.toPlayer(): Player? {
    return Bukkit.getPlayer(id)
}

inline fun YuPlayerData.toQuestMember(): QuestMember {
    return QuestMember(id)
}