package com.github.sigureruri.yuquest.quest.mission.dependency

import com.github.sigureruri.yuquest.quest.Quest

object MissionDependencyInterpreter {
    fun fulfillConditions(quest: Quest, dependency: MissionDependency): Boolean {
        val missions = quest.missions

        return when (dependency) {
            is MissionDependency.Nothing -> true
            is MissionDependency.Single -> {
                missions.any { it.id == dependency.id && it.status.isEnded() }
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