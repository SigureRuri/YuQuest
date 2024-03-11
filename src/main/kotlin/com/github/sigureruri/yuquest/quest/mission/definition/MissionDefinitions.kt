package com.github.sigureruri.yuquest.quest.mission.definition

import com.github.sigureruri.yuquest.data.identified.IdentifiedDataRepository
import com.github.sigureruri.yuquest.quest.mission.MemberRelatedEvent
import com.github.sigureruri.yuquest.util.YuId

class MissionDefinitions {

    var defaultEffect = DefaultMissionEffect()
        private set

    private val valueProviderRepository = IdentifiedDataRepository<YuId, MissionDefinition<*>>()

    val definitions: Set<MissionDefinition<*>>
        get() = valueProviderRepository.values

    fun withDefaultEffect(effect: DefaultMissionEffect): MissionDefinitions {
        defaultEffect = effect
        return this
    }

    fun <T : MemberRelatedEvent> add(definition: MissionDefinition<T>): MissionDefinitions {
        valueProviderRepository.put(definition)
        return this
    }
}