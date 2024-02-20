package com.github.sigureruri.yuquest

import com.github.sigureruri.yuquest.playerdata.PlayerDataManager
import com.github.sigureruri.yuquest.playerdata.local.LocalPlayerDataHolder
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class YuQuest : JavaPlugin() {

    private val playerDataManager = PlayerDataManager(this)

    override fun onEnable() {
        playerDataManager.init()
    }

    override fun onDisable() {
        // TODO: Save playerdata
    }
}
