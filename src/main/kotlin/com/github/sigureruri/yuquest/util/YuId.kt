package com.github.sigureruri.yuquest.util

import com.github.sigureruri.yuquest.data.identified.Identified

class YuId(override val id: String) : Identified<String>() {
    init {
        require(id.matches(NAMING_RULE)) { "id must match $NAMING_RULE" }
    }

    companion object {
        private val NAMING_RULE = "[a-z_0-9]+".toRegex()
    }
}