package com.github.sigureruri.yuquest.playerdata.bukkit

import com.github.sigureruri.yuquest.YuQuest
import com.github.sigureruri.yuquest.playerdata.PlayerDataManager
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.event.server.PluginEnableEvent

class PlayerDataListener(private val playerDataManager: PlayerDataManager) : Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun onJoin(event: PlayerJoinEvent) {
        // TODO: Load playerdata
    }

    // 本来onQuitはなくてもいいが、サーバを長期的に起動している場合にオフラインのプレイヤーの分も保存し続けることを防ぐ
    @EventHandler(priority = EventPriority.MONITOR)
    fun onQuit(event: PlayerQuitEvent) {
        // TODO: save and remove playerdata from LocalPlayerDataHolder
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onEnable(event: PluginEnableEvent) {
        if (event.plugin is YuQuest) {
            // TODO: load playerdata of online players in the server
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onDisable(event: PluginDisableEvent) {
        if (event.plugin is YuQuest) {
            // TODO: Save playerdata of online players in the server
        }
    }
}