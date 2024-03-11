package com.github.sigureruri.yuquest.quest.persistence

import com.github.sigureruri.yuquest.data.persistence.PersistentDataManipulator
import com.github.sigureruri.yuquest.quest.Quest
import java.util.*

class YamlQuestDataManipulator : PersistentDataManipulator<UUID, Quest> {
    override fun load(key: UUID): Quest {
        TODO()
    }

    override fun save(data: Quest) {
        TODO()
    }

    override fun exists(key: UUID): Boolean {
        TODO()
    }
}