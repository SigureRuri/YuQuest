package com.github.sigureruri.yuquest.playerdata.bukkit

import com.github.sigureruri.yuquest.YuQuest
import com.github.sigureruri.yuquest.playerdata.PlayerDataManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.event.server.PluginEnableEvent

class PlayerDataListener(private val dataManager: PlayerDataManager) : Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun onJoin(event: PlayerJoinEvent) {
        val playerUuid = event.player.uniqueId

        try {
            if (dataManager.canLoad(playerUuid)) {
                dataManager.load(playerUuid)
            } else {
                dataManager.createNew(playerUuid)
            }
        } catch (e: Exception) {
            Bukkit.getLogger().warning("An error was occurred when loading YuQuest's playerdata.")
            e.printStackTrace()
            event.player.kick(
                    Component.text("[YuQuest] An error was occurred when loading playerdata.", NamedTextColor.RED)
                            .append(Component.text("Please notice to server administrator", NamedTextColor.WHITE).decorate(TextDecoration.BOLD))
            )
        }


        // TODO: LocalPlayerDataRepositoryは少なくとも、オンライン中のプレイヤーについてプレイヤーデータの存在を保証*してほしい *
    }

    // 本来onQuitはなくてもいいが、サーバを長期的に起動している場合にオフラインのプレイヤーの分も保存し続けることを防ぐ
    @EventHandler(priority = EventPriority.MONITOR)
    fun onQuit(event: PlayerQuitEvent) {
        val playerUuid = event.player.uniqueId

        // TODO: save and remove playerdata from LocalPlayerDataRepository
    }
}