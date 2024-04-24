package com.github.sigureruri.yuquest.quest

import com.github.sigureruri.yuquest.data.identified.IdentifiedDataRepository
import com.github.sigureruri.yuquest.data.identified.MutableIdentifiedDataRepository
import java.util.*

class QuestTracker {
    private val trackingQuestsRepository = MutableIdentifiedDataRepository<UUID, Quest>()

    val trackingQuests: IdentifiedDataRepository<UUID, Quest>
        get() = trackingQuestsRepository

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