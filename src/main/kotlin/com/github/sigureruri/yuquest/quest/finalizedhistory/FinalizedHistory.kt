package com.github.sigureruri.yuquest.quest.finalizedhistory

import com.github.sigureruri.yuquest.data.identified.Identified
import com.github.sigureruri.yuquest.quest.Mission
import com.github.sigureruri.yuquest.quest.Quest
import com.github.sigureruri.yuquest.util.YuId
import java.util.UUID

data class FinalizedHistory(
    override val id: UUID,
    // TODO: これいるか？
    val definitionId: YuId,
    val isCompleted: Boolean,
    val missionStatus: Map<YuId, MissionFinalizedStatus>
) : Identified<UUID>() {
    enum class MissionFinalizedStatus {
        NOT_COMPLETED,
        COMPLETED
    }

    companion object {
        @JvmStatic
        fun of(quest: Quest): FinalizedHistory {
            require(quest.status.isEnded()) { "quest must be ended" }

            val isCompleted = quest.status == Quest.Status.COMPLETED
            val missionStatus = quest.missions.associate {
                val status = when (it.status) {
                    Mission.Status.COMPLETED -> MissionFinalizedStatus.COMPLETED
                    else -> MissionFinalizedStatus.NOT_COMPLETED
                }
                it.id to status
            }
            return FinalizedHistory(quest.id, quest.definition.id, isCompleted, missionStatus)
        }
    }
}