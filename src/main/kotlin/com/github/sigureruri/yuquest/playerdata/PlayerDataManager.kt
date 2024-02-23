package com.github.sigureruri.yuquest.playerdata

import com.github.sigureruri.yuquest.playerdata.bukkit.PlayerDataListener
import com.github.sigureruri.yuquest.playerdata.local.LocalPlayerDataRepository
import com.github.sigureruri.yuquest.playerdata.local.YuPlayerData
import com.github.sigureruri.yuquest.playerdata.persistence.PersistentPlayerDataManipulator
import com.github.sigureruri.yuquest.playerdata.persistence.YamlPlayerDataManipulator
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID
import kotlin.io.path.Path
import kotlin.jvm.Throws

class PlayerDataManager(private val plugin: JavaPlugin) {
    private val localDataRepository = LocalPlayerDataRepository()

    private val playerDataManipulator: PersistentPlayerDataManipulator

    init {
        require(plugin.isEnabled)

        playerDataManipulator = YamlPlayerDataManipulator(Path(""))
        plugin.server.pluginManager.registerEvents(PlayerDataListener(this), plugin)
    }

    fun existsInLocalRepository(uuid: UUID) = localDataRepository.hasPlayerData(uuid)

    fun createNew(uuid: UUID): YuPlayerData {
        val newData = YuPlayerData(uuid)
        localDataRepository.putPlayerData(newData)
        return newData
    }

    fun remove(uuid: UUID) = localDataRepository.removePlayerData(uuid)

    @Throws
    fun canLoad(uuid: UUID) = playerDataManipulator.exists(uuid)

    @Throws
    fun load(uuid: UUID): YuPlayerData {
        if (!playerDataManipulator.exists(uuid)) throw IllegalStateException("There is no playerdata")

        return playerDataManipulator.load(uuid).apply { localDataRepository.putPlayerData(this) }
    }

    @Throws
    fun save(uuid: UUID) {
        if (!localDataRepository.hasPlayerData(uuid)) throw IllegalArgumentException("There is no playerdata of $uuid")

        playerDataManipulator.save(localDataRepository.getPlayerData(uuid)!!)
    }

    fun saveAndFlush(uuid: UUID) {
        
    }

    // catch exception and print stacktrace if PersistentPlayerDataManipulator#save(uuid) throws exception.
    fun tryToSaveAndFlushAllLoadedPlayerData() {
        localDataRepository.getAllPlayerData().forEach {
            try {
                localDataRepository.removePlayerData(it.uuid)
                playerDataManipulator.save(it)
            } catch (e: Exception) {
                plugin.logger.warning("we can't save playerdata of ${it.uuid}, try to save next playerdata")
                e.printStackTrace()
            }
        }
    }
}