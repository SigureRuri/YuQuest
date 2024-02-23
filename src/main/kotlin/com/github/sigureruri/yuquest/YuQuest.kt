package com.github.sigureruri.yuquest

import com.github.sigureruri.yuquest.playerdata.PlayerDataManager
import org.bukkit.plugin.java.JavaPlugin

class YuQuest : JavaPlugin() {

    private lateinit var playerDataManager: PlayerDataManager

    override fun onEnable() {
        playerDataManager = PlayerDataManager(this)
    }

    override fun onDisable() {
        // TODO: Save playerdata
    }
}
