package com.github.sigureruri.yuquest.data.keyed

abstract class KeyedData<K>(val key: K) {
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KeyedData<*>

        return key == other.key
    }

    final override fun hashCode(): Int {
        return key?.hashCode() ?: 0
    }
}