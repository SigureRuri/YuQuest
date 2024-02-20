package com.github.sigureruri.yuquest.playerdata.persistence

import com.github.sigureruri.yuquest.playerdata.local.YuPlayerData
import java.io.File
import java.nio.file.Path
import java.util.*

class YamlPlayerDataManipulator(private val directory: File) : PersistentPlayerDataManipulator {
    init {
        if (!directory.isDirectory) throw IllegalArgumentException("playerDataDirectory must be directory")
    }

    constructor(path: Path) : this(path.toFile())

    override fun save(playerData: YuPlayerData) {

    }

    override fun load(uuid: UUID): YuPlayerData {
        TODO("Not yet implemented")
    }

    override fun exists(uuid: UUID): Boolean {
        if ()
        TODO("Not yet implemented")
    }
}