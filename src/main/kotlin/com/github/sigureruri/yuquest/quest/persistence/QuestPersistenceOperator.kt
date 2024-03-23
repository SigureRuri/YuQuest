package com.github.sigureruri.yuquest.quest.persistence

import com.github.sigureruri.yuquest.data.persistence.PersistentDataManipulator
import com.github.sigureruri.yuquest.quest.Quest
import com.github.sigureruri.yuquest.quest.QuestResourceRepository
import com.github.sigureruri.yuquest.quest.QuestTracker
import com.github.sigureruri.yuquest.quest.bukkit.QuestLoader
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

class QuestPersistenceOperator(
    private val plugin: JavaPlugin,
    private val resources: QuestResourceRepository,
    private val tracker: QuestTracker
) {
    private var isEnabled = false

    private val dataFile = File(plugin.dataFolder, "quests")

    private val questDataManipulator: PersistentDataManipulator<UUID, Quest> = YamlQuestDataManipulator(dataFile, resources, tracker)

    fun enable() {
        require(plugin.isEnabled)
        require(!isEnabled)
        isEnabled = true

        plugin.server.pluginManager.registerEvents(QuestLoader(this), plugin)
    }

    fun loadAllData() {
        questDataManipulator.getLoadableKeys().forEach {
            val quest = questDataManipulator.load(it)
            quest.requestToStartTracking()
        }
    }

    fun saveAllData() {
        // 既に終了した保存済みのデータを永続化層から削除
        questDataManipulator.getLoadableKeys()
            .filter { key -> !tracker.trackingQuests.map { it.id }.contains(key) }
            .forEach {
                questDataManipulator.remove(it)
            }

        tracker.trackingQuests.forEach {
            questDataManipulator.save(it)
        }
    }

}