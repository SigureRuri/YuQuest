package com.github.sigureruri.yuquest.playerdata

import com.github.sigureruri.yuquest.playerdata.bukkit.PlayerDataListener
import com.github.sigureruri.yuquest.playerdata.local.LocalPlayerDataHolder
import com.github.sigureruri.yuquest.playerdata.local.YuPlayerData
import com.github.sigureruri.yuquest.playerdata.persistence.PersistentPlayerDataManipulator
import com.github.sigureruri.yuquest.playerdata.persistence.YamlPlayerDataManipulator
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID
import kotlin.io.path.Path

class PlayerDataManager(private val plugin: JavaPlugin) {
    private val playerDataHolder = LocalPlayerDataHolder()

    private val playerDataManipulator: PersistentPlayerDataManipulator

    init {
        require(plugin.isEnabled)

        playerDataManipulator = YamlPlayerDataManipulator(Path(""))
        plugin.server.pluginManager.registerEvents(PlayerDataListener(this), plugin)
    }

    fun createNewPlayerData(uuid: UUID) = playerDataHolder.createNewPlayerData(uuid)
    fun canLoad(uuid: UUID) = playerDataManipulator.exists(uuid)
    fun load(uuid: UUID): YuPlayerData {
        if (!playerDataManipulator.exists(uuid)) throw IllegalStateException("There is no playerdata")

        return playerDataManipulator.load(uuid).apply { playerDataHolder.putPlayerData(uuid, this) }
    }

    fun loadOrCreateNewPlayerData(uuid: UUID): YuPlayerData {
        return if (canLoad(uuid)) {
            playerDataManipulator.load(uuid).apply { playerDataHolder.putPlayerData(uuid, this) }
        } else {
            return playerDataHolder.createNewPlayerData(uuid)
        }
    }
    fun saveLoadedPlayerData(uuid: UUID) {
        if (!playerDataHolder.hasPlayerData(uuid)) throw IllegalArgumentException("There is no playerdata of $uuid")

        playerDataManipulator.save(playerDataHolder.getPlayerData(uuid)!!)
    }

    fun tryToSaveAllLoadedPlayerData() {
        playerDataHolder.getAllPlayerData().forEach {
            try {
                playerDataManipulator.save(it)
            } catch (e: Exception) {
                plugin.logger.warning("we can't save playerdata of ${it.uuid}, try to save next playerdata")
                e.printStackTrace()
            }
        }
    }
}