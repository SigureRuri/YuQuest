package com.github.sigureruri.yuquest.quest.persistence

import com.github.sigureruri.yuquest.data.identified.IdentifiedDataRepository
import com.github.sigureruri.yuquest.data.persistence.PersistentDataManipulator
import com.github.sigureruri.yuquest.quest.*
import com.github.sigureruri.yuquest.util.YuId
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.*

private typealias MissionDefaultValue = Quest.MissionDefaultValue

class YamlQuestDataManipulator(private val dataFolder: File, private val resourceManager: QuestResourceRepository, private val tracker: QuestTracker) : PersistentDataManipulator<UUID, Quest> {
    init {
        dataFolder.mkdirs()
        require(dataFolder.isDirectory) { "dataFolder must be directory" }
    }

    override fun load(key: UUID): Quest {
        val dataFile = File(dataFolder, "$key.yml")
        val yamlConfig = YamlConfiguration.loadConfiguration(dataFile)

        val questDefinitionId = YuId(yamlConfig.getString("definition-id")!!)
        val questStatus = Quest.Status.valueOf(yamlConfig.getString("status")!!)
        val members = IdentifiedDataRepository<UUID, QuestMember>().apply {
            yamlConfig.getStringList("members")
                .map { QuestMember(UUID.fromString(it)) }
                .forEach { put(it) }
        }
        val missionsSection = yamlConfig.getConfigurationSection("missions")!!
        val missionValueProviders: Map<YuId, MissionDefaultValue> = missionsSection.getValues(false)
            .map { YuId(it.key) to it.value as ConfigurationSection }
            .associate {
                val missionStatus = Mission.Status.valueOf(it.second.getString("status")!!)
                val currentCount = it.second.getInt("currentCount")
                it.first to MissionDefaultValue(missionStatus, currentCount)
            }

        val questDefinition = resourceManager.getQuestDefinition(questDefinitionId)

        // TODO: PersistentDataManipulatorは永続化されたデータへ(から)のオブジェクトの保存(復元)
        // TODO: に責務をとどめるべきであって、当該オブジェクトのTracking状態まで管理するべきでない
        return Quest(key, tracker, questDefinition, questStatus, members, missionValueProviders)
    }

    override fun save(data: Quest) {
        val dataFile = File(dataFolder, "${data.id}.yml")
        if (!dataFile.exists() || dataFile.isDirectory) {
            dataFile.parentFile.mkdirs()
            dataFile.createNewFile()
        }

        val yamlConfig = YamlConfiguration()

        with(yamlConfig) {
            set("definition-id", data.definition.id.id)
            set("status", data.status.toString())
            set("members", data.members.map { it.id.toString() })
            with(createSection("missions")) {
                data.missions.forEach {
                    with(createSection(it.id.id)) {
                        set("status", it.status.toString())
                        set("currentCount", it.count)
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
                println("するよ")
                println(file.isFile && file.extension == "yml")
                file.isFile && file.extension == "yml"
            }.mapNotNull {
                try {
                    UUID.fromString(it.nameWithoutExtension)
                } catch (e: IllegalArgumentException) {
                    return@mapNotNull null
                }
            }.toTypedArray()
        )
    }
}