package com.github.sigureruri.yuquest.quest

data class QuestId(val id: String) {
    init {
        if (!id.matches(NAMING_RULE)) throw IllegalArgumentException("id must match $NAMING_RULE")
    }

    companion object {
        private val NAMING_RULE = "[a-z_0-9]+".toRegex()
    }
}
