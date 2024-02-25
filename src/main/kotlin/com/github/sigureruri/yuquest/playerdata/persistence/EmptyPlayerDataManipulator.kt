package com.github.sigureruri.yuquest.playerdata.persistence

import com.github.sigureruri.yuquest.persistence.PersistentDataManipulator
import com.github.sigureruri.yuquest.playerdata.local.YuPlayerData
import java.util.*

@Deprecated("This class should be removed in production release")
class EmptyPlayerDataManipulator : PersistentDataManipulator<UUID, YuPlayerData> {
    override fun load(key: UUID): YuPlayerData {
        return YuPlayerData(key)
    }

    override fun save(data: YuPlayerData) {
        // DO NOTHING
    }

    override fun exists(key: UUID): Boolean {
        return false
    }
}