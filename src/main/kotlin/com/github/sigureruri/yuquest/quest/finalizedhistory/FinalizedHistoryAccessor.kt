package com.github.sigureruri.yuquest.quest.finalizedhistory

import java.util.UUID

interface FinalizedHistoryAccessor {
    fun hasFinalizedHistory(uuid: UUID): Boolean

    fun getFinalizedHistory(uuid: UUID): FinalizedHistory?
}