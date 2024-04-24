package com.github.sigureruri.yuquest.data.identified

/**
 * 特定の[Identified]を、keyとvalueに分けて効率的にデータ探索をするミュータブルなクラス
 *
 * @param K
 * @param V
 */
class MutableIdentifiedDataRepository<K, V : Identified<K>>(data: Set<V> = setOf()) : IdentifiedDataRepository<K, V>(data) {
    fun remove(id: K) {
        dataMap.remove(id)
    }

    fun remove(data: V) {
        dataMap.remove(data.id)
    }

    fun put(data: V) {
        dataMap[data.id] = data
    }
}