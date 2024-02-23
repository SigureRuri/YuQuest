package com.github.sigureruri.yuquest.playerdata.persistence

import com.github.sigureruri.yuquest.playerdata.local.YuPlayerData
import java.util.UUID
import kotlin.jvm.Throws

/**
 * プレイヤーデータの永続化を担当するインターフェース
 * このインターフェースの実装からは、永続化中に起きた例外が投げられる可能性がある
 */
interface PersistentPlayerDataManipulator {
    @Throws
    fun save(playerData: YuPlayerData)

    @Throws
    fun load(uuid: UUID): YuPlayerData

    @Throws
    fun exists(uuid: UUID): Boolean
}