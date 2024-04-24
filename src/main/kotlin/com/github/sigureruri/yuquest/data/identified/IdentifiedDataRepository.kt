package com.github.sigureruri.yuquest.data.identified

/**
 * 特定の[Identified]を、keyとvalueに分けて効率的にデータ探索をするクラス
 *
 * @param K
 * @param V
 */
open class IdentifiedDataRepository<K, V : Identified<K>>(data: Set<V> = setOf()) {
    init {
        data.forEach { dataMap[it.id] = it }
    }

    protected val dataMap = mutableMapOf<K, V>()

    operator fun get(id: K) = dataMap[id]

    val values: Set<V>
        get() = dataMap.values.toSet()

    fun has(id: K) = dataMap.contains(id)

    fun has(data: V) = dataMap.contains(data.id)
}