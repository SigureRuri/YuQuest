package com.github.sigureruri.yuquest.playerdata.local

import java.util.UUID

class LocalPlayerDataHolder {
    private val playerDataMap = mutableMapOf<UUID, YuPlayerData>()

    fun getPlayerData(uuid: UUID) = playerDataMap[uuid]

    fun hasPlayerData(uuid: UUID) = playerDataMap.contains(uuid)

    fun removePlayerData(uuid: UUID): Unit {
        playerDataMap.remove(uuid)
    }



    fun createNewPlayerData(uuid: UUID): YuPlayerData {
        if (playerDataMap.contains(uuid)) throw IllegalStateException("There is already $uuid`s playerdata")
        val newPlayerData = YuPlayerData()
        playerDataMap[uuid] = newPlayerData
        return newPlayerData
    }
}