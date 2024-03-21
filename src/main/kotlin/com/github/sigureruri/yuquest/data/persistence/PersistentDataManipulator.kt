package com.github.sigureruri.yuquest.data.persistence

/**
 * [K]に対し一意に定まる[V]の永続化を扱う
 */
interface PersistentDataManipulator<K, V> {
    @Throws
    fun save(data: V)

    @Throws
    fun load(key: K): V

    @Throws
    fun remove(key: K): Boolean

    @Throws
    fun exists(key: K): Boolean

    @Throws
    fun getLoadableKeys(): Set<K>
}