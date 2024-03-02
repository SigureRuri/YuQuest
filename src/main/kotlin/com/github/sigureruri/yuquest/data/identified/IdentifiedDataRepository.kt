package com.github.sigureruri.yuquest.data.identified

/**
 * 特定の[Identified]を、keyとvalueに分けて効率的にデータ探索をするクラス
 *
 * @param K
 * @param V
 */
class IdentifiedDataRepository<K, V : Identified<K>> {
    private val dataMap = mutableMapOf<K, V>()

    operator fun get(key: K) = dataMap[key]

    // toList() works as deep copy
    val values: List<V>
        get() = dataMap.values.toList()

    fun has(key: K) = dataMap.contains(key)

    fun remove(key: K) {
        dataMap.remove(key)
    }

    fun put(data: V) {
        dataMap[data.id] = data
    }
}