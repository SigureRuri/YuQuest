package com.github.sigureruri.yuquest.playerdata

import com.github.sigureruri.yuquest.playerdata.local.YuPlayerData
import java.util.*

interface PlayerDataAccessor {
    /** このUUIDを持つプレイヤーがオンラインであれば、返り値がnull出ないことが保証される */
    fun getFromLocalRepository(uuid: UUID): YuPlayerData?

    fun getAllFromLocalRepository(): Set<YuPlayerData>
}