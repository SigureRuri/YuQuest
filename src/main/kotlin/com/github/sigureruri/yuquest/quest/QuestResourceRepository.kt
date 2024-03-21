package com.github.sigureruri.yuquest.quest

import com.github.sigureruri.yuquest.data.identified.IdentifiedDataRepository
import com.github.sigureruri.yuquest.quest.definition.QuestDefinition
import com.github.sigureruri.yuquest.quest.missiontype.MissionType
import com.github.sigureruri.yuquest.util.YuId

class QuestResourceRepository {
    private val questDefinitionRepository = IdentifiedDataRepository<YuId, QuestDefinition>()

    private val missionTypeRepository = IdentifiedDataRepository<YuId, MissionType<*>>()

    fun registerQuestDefinition(questDefinition: QuestDefinition) {
        if (questDefinitionRepository.has(questDefinition)) throw IllegalArgumentException("${questDefinition.id} has already been registered")

        questDefinitionRepository.put(questDefinition)
    }

    fun registerMissionType(missionType: MissionType<*>) {
        if (missionTypeRepository.has(missionType)) throw IllegalStateException("${missionType.id} has already been registered")

        missionTypeRepository.put(missionType)
    }

    fun getQuestDefinition(id: YuId): QuestDefinition {
        return questDefinitionRepository[id] ?: throw IllegalArgumentException("$id is not registered")
    }

    fun getMissionType(id: YuId): MissionType<*> {
        return missionTypeRepository[id] ?: throw IllegalArgumentException("$id is not registered.")
    }

    fun getQuestDefinitions() = questDefinitionRepository.values

    fun getMissionTypes() = missionTypeRepository.values
}