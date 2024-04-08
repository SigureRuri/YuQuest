package com.github.sigureruri.yuquest.quest.persistence.finalizedquesthistory

import com.github.sigureruri.yuquest.data.persistence.PersistentDataManipulator
import com.github.sigureruri.yuquest.quest.finalizedhistory.FinalizedHistory
import com.github.sigureruri.yuquest.util.YuId
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.*

class YamlFinalizedHistoryDataManipulator(private val dataFolder: File) : PersistentDataManipulator<UUID, FinalizedHistory> {
    init {
        dataFolder.mkdirs()
        require(dataFolder.isDirectory) { "dataFolder must be directory" }
    }

    override fun load(key: UUID): FinalizedHistory {
        val dataFile = File(dataFolder, "$key.yml")
        val yamlConfig = YamlConfiguration.loadConfiguration(dataFile)

        val definitionId = YuId(yamlConfig.getString("definition-id")!!)
        val isCompleted = yamlConfig.getBoolean("is-completed")
        val missionStatus = yamlConfig.getConfigurationSection("mission-status")!!.getValues(false)
            .map { YuId(it.key) to it.value as ConfigurationSection }
            .associate {
                it.first to FinalizedHistory.MissionFinalizedStatus.valueOf(it.second.getString("status")!!)
            }

        return FinalizedHistory(key, definitionId, isCompleted, missionStatus)
    }

    override fun save(data: FinalizedHistory) {
        val dataFile = File(dataFolder, "${data.id}.yml")
        if (!dataFile.exists() || dataFile.isDirectory) {
            dataFile.parentFile.mkdirs()
            dataFile.createNewFile()
        }

        val yamlConfig = YamlConfiguration()

        with(yamlConfig) {
            set("definition-id", data.definitionId.id)
            set("is-completed", data.isCompleted)
            with(createSection("mission-status")) {
                data.missionStatus.forEach { (mission, status) ->
                    with(createSection(mission.id)) {
                        set("status", status.toString())
                    }
                }
            }
        }

        yamlConfig.save(dataFile)
    }

    override fun remove(key: UUID): Boolean {
        val dataFile = File(dataFolder, "${key}.yml")
        if (!dataFile.exists() || dataFile.isDirectory) return false

        return dataFile.delete()
    }

    override fun exists(key: UUID): Boolean {
        val dataFile = File(dataFolder, "$key.yml")

        return dataFile.exists()
    }

    override fun getLoadableKeys(): Set<UUID> {
        return setOf(
            *dataFolder.listFiles { file ->
                file.isFile && file.extension == "yml"
            }!!.mapNotNull {
                try {
                    UUID.fromString(it.nameWithoutExtension)
                } catch (e: IllegalArgumentException) {
                    return@mapNotNull null
                }
            }.toTypedArray()
        )
    }
}