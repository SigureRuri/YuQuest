package com.github.sigureruri.yuquest.playerdata.local

import com.github.sigureruri.yuquest.data.identified.Identified
import com.github.sigureruri.yuquest.util.YuId

class MissionAdaptiveStatus(override val id: YuId) : Identified<YuId>() {
    var initialized = false

    var finalized = false

    var completed = false
}