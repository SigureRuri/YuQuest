package com.github.sigureruri.yuquest.playerdata.persistence

import com.github.sigureruri.yuquest.playerdata.local.YuPlayerData
import java.util.UUID

interface PersistentPlayerDataManipulator {
    fun save(playerData: YuPlayerData)

    fun load(uuid: UUID): YuPlayerData

    fun exists(uuid: UUID): Boolean
}