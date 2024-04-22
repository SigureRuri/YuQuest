package com.github.sigureruri.yuquest.quest.persistence.finalizedhistory

import com.github.sigureruri.yuquest.data.identified.IdentifiedDataRepository
import com.github.sigureruri.yuquest.data.persistence.PersistentDataManipulator
import com.github.sigureruri.yuquest.quest.finalizedhistory.FinalizedHistory
import com.github.sigureruri.yuquest.quest.finalizedhistory.FinalizedHistoryAccessor
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

class FinalizedHistoryPersistenceOperator(private val plugin: JavaPlugin) : FinalizedHistoryAccessor, FinalizedHistoryWriter {
    private val dataFile = File(plugin.dataFolder, "questhistory")

    private val historyManipulator: PersistentDataManipulator<UUID, FinalizedHistory> = YamlFinalizedHistoryDataManipulator(dataFile)

    private val cache = IdentifiedDataRepository<UUID, FinalizedHistory>()

    override fun hasFinalizedHistory(uuid: UUID): Boolean {
        return cache.has(uuid) || historyManipulator.exists(uuid)
    }

    override fun getFinalizedHistory(uuid: UUID): FinalizedHistory? {
        if (cache.has(uuid)) {
            return cache[uuid]
        } else {
            return try {
                historyManipulator.load(uuid)
            } catch (_: Exception) {
                return null
            }
        }
    }

    override fun writeFinalizedHistory(history: FinalizedHistory) {
        historyManipulator.save(history)
    }
}