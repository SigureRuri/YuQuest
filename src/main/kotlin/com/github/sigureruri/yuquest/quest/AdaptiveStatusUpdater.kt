package com.github.sigureruri.yuquest.quest

import com.github.sigureruri.yuquest.playerdata.local.YuPlayerData
import com.github.sigureruri.yuquest.quest.finalizedhistory.FinalizedHistory
import com.github.sigureruri.yuquest.util.toQuestMember

// TODO: このへんどうにかする
// TODO: trackingQuestsがIdentifiedDataRepositoryが返すと嬉しい
/*
 * Updateが必要なとき：
 *
 * クエスト側
 * ・メンバー追加、削除時
 * ・ミッション開始時
 * ・ミッション終了時
 * ・クエスト開始時
 * ・クエスト終了時
 *
 * プレイヤー側
 * ・ログイン時
 *
 *
 * 対処：
 * 1. 毎Tick回してもいいが、即時的な対応ができないだけでなく、負荷の面でも好ましくない。
 */
class AdaptiveStatusUpdater(private val questManager: QuestManager, private val target: YuPlayerData) {
    private val historyAccessor = questManager.finalizedHistoryAccessor

    private val questMember = QuestMember(target.id)

    fun update() {
        val trackingQuests = questManager.trackingQuests

        target.questAdaptiveStatus.values.forEach { questAdaptiveStatus ->
            val missionAdaptiveStatus = questAdaptiveStatus.missionsAdaptiveStatus.values

            // trackingQuestsにquestAdaptiveStatusが含まれていれば。
            if (trackingQuests.values.any { it.id == questAdaptiveStatus.id }) {
                val quest = trackingQuests[questAdaptiveStatus.id]!!
                val defaultEffect = quest.definition.missionDefinitions.defaultEffect

                missionAdaptiveStatus.forEach {
                    val mission = quest.missions[it.id]!!
                    val missionDefinition = mission.definition
                    if (mission.status != Mission.Status.NOT_STARTED_YET && !it.initialized) {
                        missionDefinition.initializeForEachMember(questMember)
                        defaultEffect.initializeForEachMember(questMember)

                        it.initialized = true
                    }
                    if (mission.status.isEnded() && !it.finalized) {
                        missionDefinition.finalizeForEachMember(questMember)
                        defaultEffect.finalizeForEachMember(questMember)
                        it.finalized = true
                    }
                    if (mission.status == Mission.Status.COMPLETED && !it.completed) {
                        missionDefinition.completeForEachMember(questMember)
                        defaultEffect.completeForEachMember(questMember)
                        it.completed = true
                    }
                }

            // historyAccessorにquestAdaptiveStatusが含まれていたら。
            // historyAccessorの存在は、それ自身がquestが終了ずみであることを意味する
            } else if (historyAccessor.hasFinalizedHistory(questAdaptiveStatus.id)) {
                val history = historyAccessor.getFinalizedHistory(questAdaptiveStatus.id)!!
                val questDefinition = questManager.resourceManager.getQuestDefinition(history.definitionId)

                missionAdaptiveStatus.forEach {
                    val missionDefinition = questDefinition.missionDefinitions.definitions[it.id]!!
                    val missionStatus = history.missionStatus[it.id]!!

                    if (!it.initialized) {
                        missionDefinition.initializeForEachMember(questMember)
                        it.initialized = true
                    }
                    if (!it.finalized) {
                        missionDefinition.finalizeForEachMember(questMember)
                        it.finalized = true
                    }
                    if (missionStatus == FinalizedHistory.MissionFinalizedStatus.COMPLETED && !it.completed) {
                        missionDefinition.completeForEachMember(questMember)
                        it.completed = true
                    }
                }
            }
        }
    }
}