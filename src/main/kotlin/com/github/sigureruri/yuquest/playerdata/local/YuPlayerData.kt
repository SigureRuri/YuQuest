package com.github.sigureruri.yuquest.playerdata.local

import com.github.sigureruri.yuquest.data.identified.Identified
import com.github.sigureruri.yuquest.data.identified.IdentifiedDataRepository
import java.util.*

class YuPlayerData(override val id: UUID) : Identified<UUID>() {
    val questAdaptiveStatus = IdentifiedDataRepository<UUID, QuestAdaptiveStatus>()
}