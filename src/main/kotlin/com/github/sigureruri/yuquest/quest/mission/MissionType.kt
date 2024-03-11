package com.github.sigureruri.yuquest.quest.mission

import com.github.sigureruri.yuquest.data.identified.Identified
import com.github.sigureruri.yuquest.util.YuId

abstract class MissionType<T : MemberRelatedEvent> : Identified<YuId>() {
    /**
     * サーバー初期化後にMissionTypeを初期化するために呼び出される
     */
    open fun initializeAfterServerStarts() {
        // DO NOTHING
    }

    internal fun isInstanceOf(missionType: MissionType<*>): Boolean {
        return missionType::class.java.isAssignableFrom(javaClass)
    }
}