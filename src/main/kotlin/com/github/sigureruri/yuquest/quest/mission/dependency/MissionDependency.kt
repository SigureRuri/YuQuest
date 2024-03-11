package com.github.sigureruri.yuquest.quest.mission.dependency

import com.github.sigureruri.yuquest.util.YuId

sealed interface MissionDependency {
    object Nothing : MissionDependency
    class Single(val id: YuId) : MissionDependency

    sealed class Double(val first: MissionDependency, val second: MissionDependency) : MissionDependency
    class And(first: MissionDependency, second: MissionDependency) : Double(first, second)
    class Or(first: MissionDependency, second: MissionDependency) : Double(first, second)
}

fun YuId.asMissionDependency() = MissionDependency.Single(this)