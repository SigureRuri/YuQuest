package com.github.sigureruri.yuquest.quest.persistence.finalizedquesthistory

import com.github.sigureruri.yuquest.quest.finalizedhistory.FinalizedHistory

interface FinalizedHistoryWriter {
    fun writeFinalizedHistory(history: FinalizedHistory)
}