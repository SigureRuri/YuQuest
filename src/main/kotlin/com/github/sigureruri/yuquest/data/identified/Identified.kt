package com.github.sigureruri.yuquest.data.identified

abstract class Identified<K> {
    abstract val id: K

    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Identified<*>

        return id == other.id
    }

    final override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Identified(id=$id)"
    }
}