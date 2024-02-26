package com.github.sigureruri.yuquest.playerdata.persistence

import com.github.sigureruri.yuquest.data.persistence.PersistentDataManipulator
import com.github.sigureruri.yuquest.playerdata.local.YuPlayerData
import java.io.File
import java.nio.file.Path
import java.util.*

class YamlPlayerDataManipulator(private val directory: File) : PersistentDataManipulator<UUID, YuPlayerData> {
    init {
        if (!directory.isDirectory) throw IllegalArgumentException("playerDataDirectory must be directory")
    }

    constructor(path: Path) : this(path.toFile())

    constructor(path: String) : this(File(path))

    override fun save(data: YuPlayerData) {
        TODO("Not yet implemented")
    }

    override fun load(key: UUID): YuPlayerData {
        TODO("Not yet implemented")
    }

    override fun exists(key: UUID): Boolean {
        TODO("Not yet implemented")
    }
}