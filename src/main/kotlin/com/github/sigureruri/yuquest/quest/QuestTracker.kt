package com.github.sigureruri.yuquest.quest

import com.github.sigureruri.yuquest.data.identified.IdentifiedDataRepository
import java.util.*

class QuestTracker {
    private val trackingQuestsRepository = IdentifiedDataRepository<UUID, Quest>()

    val trackingQuests: List<Quest>
        get() = trackingQuestsRepository.values

    fun startTracking(quest: Quest) {
        require(!trackingQuestsRepository.has(quest)) { "$quest already tracked" }

        trackingQuestsRepository.put(quest)
    }

    fun endTracking(quest: Quest) {
        require(trackingQuestsRepository.has(quest)) { "$quest is not tracked" }
        require(quest.status.isEnded()) { "$quest is active" }

        trackingQuestsRepository.remove(quest)
    }
}