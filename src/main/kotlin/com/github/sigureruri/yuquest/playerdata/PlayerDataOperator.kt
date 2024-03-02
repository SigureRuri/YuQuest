package com.github.sigureruri.yuquest.playerdata

import com.github.sigureruri.yuquest.data.identified.IdentifiedDataRepository
import com.github.sigureruri.yuquest.data.persistence.PersistentDataManipulator
import com.github.sigureruri.yuquest.playerdata.bukkit.PlayerDataListener
import com.github.sigureruri.yuquest.playerdata.local.YuPlayerData
import com.github.sigureruri.yuquest.playerdata.persistence.EmptyPlayerDataManipulator
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class PlayerDataOperator(private val plugin: JavaPlugin) : PlayerDataAccessor {
    private val localDataRepository = IdentifiedDataRepository<UUID, YuPlayerData>()

    private val playerDataManipulator: PersistentDataManipulator<UUID, YuPlayerData>

    init {
        require(plugin.isEnabled)

        // TODO: Replace EmptyPlayerDataManipulator with YamlPlayerDataManipulator
        playerDataManipulator = EmptyPlayerDataManipulator()
        plugin.server.pluginManager.registerEvents(PlayerDataListener(this, plugin.logger), plugin)
    }

    override fun getFromLocalRepository(uuid: UUID): YuPlayerData? = localDataRepository.get(uuid)

    override fun getAllFromLocalRepository(): List<YuPlayerData> = localDataRepository.values

    fun existsInLocalRepository(uuid: UUID) = localDataRepository.has(uuid)

    fun createNew(uuid: UUID): YuPlayerData {
        val newData = YuPlayerData(uuid)
        localDataRepository.put(newData)
        return newData
    }

    fun removeFromLocalRepository(uuid: UUID) = localDataRepository.remove(uuid)

    @Throws
    fun canLoad(uuid: UUID) = playerDataManipulator.exists(uuid)

    @Throws
    fun load(uuid: UUID): YuPlayerData {
        if (!playerDataManipulator.exists(uuid)) throw IllegalStateException("There is no playerdata")

        return playerDataManipulator.load(uuid).apply { localDataRepository.put(this) }
    }

    @Throws
    fun save(uuid: UUID) {
        if (!localDataRepository.has(uuid)) throw IllegalArgumentException("There is no playerdata of $uuid")

        playerDataManipulator.save(localDataRepository[uuid]!!)
    }
}