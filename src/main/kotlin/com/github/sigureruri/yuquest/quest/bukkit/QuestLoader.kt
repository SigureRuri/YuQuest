package com.github.sigureruri.yuquest.quest.bukkit

import com.github.sigureruri.yuquest.YuQuest
import com.github.sigureruri.yuquest.quest.persistence.quest.QuestPersistenceOperator
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.event.server.PluginEnableEvent

class QuestLoader(private val persistenceOperator: QuestPersistenceOperator) : Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun onYuQuestEnable(event: PluginEnableEvent) {
        if (event.plugin !is YuQuest) return

        persistenceOperator.loadAllData()
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onYuQuestDisable(event: PluginDisableEvent) {
        if (event.plugin !is YuQuest) return

        persistenceOperator.saveAllData()
    }
}