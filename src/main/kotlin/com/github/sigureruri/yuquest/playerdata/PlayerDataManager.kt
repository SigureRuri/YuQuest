package com.github.sigureruri.yuquest.playerdata

import com.github.sigureruri.yuquest.playerdata.bukkit.PlayerDataListener
import org.bukkit.plugin.java.JavaPlugin

class PlayerDataManager(private val plugin: JavaPlugin) {
    private var isInitialized = false

    fun init() {
        if (isInitialized) return
        plugin.server.pluginManager.registerEvents(PlayerDataListener(), plugin)
        isInitialized = true
    }
}