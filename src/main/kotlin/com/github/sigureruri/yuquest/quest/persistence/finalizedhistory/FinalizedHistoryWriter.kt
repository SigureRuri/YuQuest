package com.github.sigureruri.yuquest.quest.persistence.finalizedhistory

import com.github.sigureruri.yuquest.quest.finalizedhistory.FinalizedHistory

interface FinalizedHistoryWriter {
    fun writeFinalizedHistory(history: FinalizedHistory)
}