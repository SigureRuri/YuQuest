package com.github.sigureruri.yuquest.playerdata.local

import java.util.UUID

data class YuPlayerData(
    val uuid: UUID
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is YuPlayerData && other.uuid == uuid) return true
        return false
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}