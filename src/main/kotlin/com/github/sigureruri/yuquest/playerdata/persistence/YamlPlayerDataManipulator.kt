package com.github.sigureruri.yuquest.playerdata.persistence

import com.github.sigureruri.yuquest.data.persistence.PersistentDataManipulator
import com.github.sigureruri.yuquest.playerdata.local.YuPlayerData
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.*

class YamlPlayerDataManipulator(private val dataFolder: File) : PersistentDataManipulator<UUID, YuPlayerData> {
    init {
        dataFolder.mkdirs()
        require(dataFolder.isDirectory) { "dataFolder must be directory" }
    }

    override fun load(key: UUID): YuPlayerData {
        val dataFile = File(dataFolder, "$key.yml")
        val yamlConfig = YamlConfiguration.loadConfiguration(dataFile)
        return YuPlayerData(key)
    }

    override fun save(data: YuPlayerData) {
        val dataFile = File(dataFolder, "${data.id}.yml")
        if (!dataFile.exists() || dataFile.isDirectory) {
            dataFile.parentFile.mkdirs()
            dataFile.createNewFile()
        }

        val yamlConfig = YamlConfiguration()

        yamlConfig.save(dataFile)
    }

    override fun exists(key: UUID): Boolean {
        val playerDataFile = File(dataFolder, "$key.yml")

        return playerDataFile.exists()
    }
}