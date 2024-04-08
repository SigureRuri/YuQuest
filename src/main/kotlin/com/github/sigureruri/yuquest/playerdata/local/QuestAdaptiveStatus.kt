package com.github.sigureruri.yuquest.playerdata.local

import com.github.sigureruri.yuquest.data.identified.Identified
import com.github.sigureruri.yuquest.data.identified.IdentifiedDataRepository
import com.github.sigureruri.yuquest.util.YuId
import java.util.UUID

class QuestAdaptiveStatus(override val id: UUID) : Identified<UUID>() {
    var initialized = false

    var finalized = false

    var completed = false

    val missionsAdaptiveStatus = IdentifiedDataRepository<YuId, MissionAdaptiveStatus>()
}