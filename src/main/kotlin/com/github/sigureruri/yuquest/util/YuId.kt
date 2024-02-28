package com.github.sigureruri.yuquest.util

data class YuId(val id: String) {
    init {
        require(id.matches(NAMING_RULE)) { "id must match $NAMING_RULE" }
    }

    companion object {
        private val NAMING_RULE = "[a-z_0-9]+".toRegex()
    }
}
