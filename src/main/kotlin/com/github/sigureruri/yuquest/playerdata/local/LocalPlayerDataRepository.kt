package com.github.sigureruri.yuquest.playerdata.local

import java.util.UUID

class LocalPlayerDataRepository {
    // keyのuuidと、valueに含まれるYuPlayerDataのuuidは同一であることをこのクラスをもって保証する
    private val playerDataMap = mutableMapOf<UUID, YuPlayerData>()

    fun getPlayerData(uuid: UUID) = playerDataMap[uuid]

    // toList() works as deep copy
    fun getAllPlayerData(): List<YuPlayerData> = playerDataMap.values.toList()

    fun hasPlayerData(uuid: UUID) = playerDataMap.contains(uuid)

    fun removePlayerData(uuid: UUID): Unit {
        playerDataMap.remove(uuid)
    }

    fun putPlayerData(playerData: YuPlayerData) {
        playerDataMap[playerData.uuid] = playerData
    }
}