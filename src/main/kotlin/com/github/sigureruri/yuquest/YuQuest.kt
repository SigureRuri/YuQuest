package com.github.sigureruri.yuquest

import com.github.sigureruri.yuquest.playerdata.PlayerDataAccessor
import com.github.sigureruri.yuquest.playerdata.PlayerDataOperator
import org.bukkit.plugin.java.JavaPlugin

class YuQuest : JavaPlugin() {

    private lateinit var playerDataOperator: PlayerDataOperator

    val playerDataAccessor: PlayerDataAccessor
        get() = playerDataOperator

    override fun onEnable() {
        playerDataOperator = PlayerDataOperator(this)
    }

    override fun onDisable() {
        // TODO: Save playerdata
    }
}
