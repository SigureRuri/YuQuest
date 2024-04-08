package com.github.sigureruri.yuquest.quest

import com.github.sigureruri.yuquest.YuQuest
import com.github.sigureruri.yuquest.quest.finalizedhistory.FinalizedHistoryAccessor

// TODO: このへんどうにかする
class AdaptiveStatusUpdater(private val historyAccessor: FinalizedHistoryAccessor) {
    fun tryToUpdate(target: QuestMember): Boolean {
        val questManager = YuQuest.INSTANCE.questManager
        val trackingQuests = questManager.trackingQuests
        val playerData = YuQuest.INSTANCE.playerDataAccessor.getFromLocalRepository(target.id) ?: return false

        playerData.questAdaptiveStatus.values.forEach {
            val history = historyAccessor.getFinalizedHistory(it.id) ?: return@forEach
        }
    }
}