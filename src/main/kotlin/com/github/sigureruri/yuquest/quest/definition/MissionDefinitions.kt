package com.github.sigureruri.yuquest.quest.definition

import com.github.sigureruri.yuquest.data.identified.IdentifiedDataRepository
import com.github.sigureruri.yuquest.data.identified.MutableIdentifiedDataRepository
import com.github.sigureruri.yuquest.quest.missiontype.MemberRelatedEvent
import com.github.sigureruri.yuquest.util.YuId

class MissionDefinitions {

    var defaultEffect = DefaultMissionEffect()
        private set

    private val definitionsRepository = MutableIdentifiedDataRepository<YuId, MissionDefinition<*>>()

    val definitions: IdentifiedDataRepository<YuId, MissionDefinition<*>>
        get() = definitionsRepository

    fun withDefaultEffect(effect: DefaultMissionEffect): MissionDefinitions {
        defaultEffect = effect
        return this
    }

    fun <T : MemberRelatedEvent> add(definition: MissionDefinition<T>): MissionDefinitions {
        definitionsRepository.put(definition)
        return this
    }
}