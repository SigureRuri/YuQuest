package com.github.sigureruri.yuquest.quest.util

import com.github.sigureruri.yuquest.quest.Quest
import com.github.sigureruri.yuquest.quest.definition.MissionDependency

object MissionDependencyInterpreter {
    fun fulfillConditions(quest: Quest, dependency: MissionDependency): Boolean {
        val missions = quest.missions

        return when (dependency) {
            is MissionDependency.Nothing -> true
            is MissionDependency.Single -> {
                missions.values.any { it.id == dependency.id && it.status.isEnded() }
            }
            is MissionDependency.And -> {
                fulfillConditions(quest, dependency.first) && fulfillConditions(quest, dependency.second)
            }
            is MissionDependency.Or -> {
                fulfillConditions(quest, dependency.first) || fulfillConditions(quest, dependency.second)
            }
        }
    }
}