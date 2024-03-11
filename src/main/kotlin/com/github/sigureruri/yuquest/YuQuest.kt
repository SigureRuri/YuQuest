package com.github.sigureruri.yuquest

import com.github.sigureruri.yuquest.playerdata.PlayerDataAccessor
import com.github.sigureruri.yuquest.playerdata.PlayerDataOperator
import com.github.sigureruri.yuquest.quest.QuestManager
import org.bukkit.plugin.java.JavaPlugin

class YuQuest : JavaPlugin() {

    private lateinit var playerDataOperator: PlayerDataOperator
        private set

    val playerDataAccessor: PlayerDataAccessor
        get() = playerDataOperator

    lateinit var questManager: QuestManager
        private set

    override fun onEnable() {
        INSTANCE = this

        playerDataOperator = PlayerDataOperator(this)
        questManager = QuestManager()

        questManager.resourceManager.getMissionTypes().forEach {
            it.initializeAfterServerStarts()
        }
    }

    override fun onDisable() {
        // TODO: Save playerdata
    }

    companion object {
        @JvmStatic
        lateinit var INSTANCE: YuQuest
            private set
    }
}
