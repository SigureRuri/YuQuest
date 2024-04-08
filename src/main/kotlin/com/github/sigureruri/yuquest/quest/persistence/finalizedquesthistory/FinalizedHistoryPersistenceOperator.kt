package com.github.sigureruri.yuquest.quest.persistence.finalizedquesthistory

import com.github.sigureruri.yuquest.data.persistence.PersistentDataManipulator
import com.github.sigureruri.yuquest.quest.finalizedhistory.FinalizedHistory
import com.github.sigureruri.yuquest.quest.finalizedhistory.FinalizedHistoryAccessor
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

class FinalizedHistoryPersistenceOperator(private val plugin: JavaPlugin) : FinalizedHistoryAccessor, FinalizedHistoryWriter {
    private val dataFile = File(plugin.dataFolder, "questhistory")

    private val historyManipulator: PersistentDataManipulator<UUID, FinalizedHistory> = YamlFinalizedHistoryDataManipulator(dataFile)

    override fun hasFinalizedHistory(uuid: UUID): Boolean {
        return historyManipulator.exists(uuid)
    }

    override fun getFinalizedHistory(uuid: UUID): FinalizedHistory? {
        return try {
            historyManipulator.load(uuid)
        } catch (_: Exception) {
            return null
        }
    }

    override fun writeFinalizedHistory(history: FinalizedHistory) {
        historyManipulator.save(history)
    }
}