package com.github.sigureruri.yuquest.persistence

interface PersistentDataManipulator<K, V> {
    @Throws
    fun save(data: V)

    @Throws
    fun load(key: K): V

    @Throws
    fun exists(key: K): Boolean
}