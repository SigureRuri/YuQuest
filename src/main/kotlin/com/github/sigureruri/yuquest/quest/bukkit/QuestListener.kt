package com.github.sigureruri.yuquest.quest.bukkit

import com.github.sigureruri.yuquest.YuQuest
import com.github.sigureruri.yuquest.quest.QuestManager
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.server.PluginEnableEvent

class QuestListener(private val questManager: QuestManager) : Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun onYuQuestEnable(event: PluginEnableEvent) {
        if (event.plugin !is YuQuest) return

        questManager.resourceManager.getMissionTypes().forEach {
            it.initializeAfterServerStarts()
        }
    }
}