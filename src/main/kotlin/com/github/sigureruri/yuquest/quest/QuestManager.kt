package com.github.sigureruri.yuquest.quest

import com.github.sigureruri.yuquest.YuQuest
import com.github.sigureruri.yuquest.quest.bukkit.QuestListener
import com.github.sigureruri.yuquest.quest.mission.MemberRelatedEvent
import com.github.sigureruri.yuquest.quest.mission.Mission
import com.github.sigureruri.yuquest.quest.mission.MissionType
import com.github.sigureruri.yuquest.quest.persistence.QuestPersistenceOperator
import com.github.sigureruri.yuquest.quest.repository.QuestResourceRepository

class QuestManager(plugin: YuQuest) {
    val resourceManager = QuestResourceRepository()

    private val tracker = QuestTracker()

    private val persistenceOperator = QuestPersistenceOperator(plugin, resourceManager, tracker)

    init {
        require(plugin.isEnabled)

        plugin.server.pluginManager.registerEvents(QuestListener(this), plugin)
    }

    val trackingQuests: Set<Quest>
        get() = tracker.trackingQuests

    fun start(definition: QuestDefinition) {
        val quest = Quest(tracker, definition)
        quest.start()
    }

    fun startWith(definition: QuestDefinition, members: Set<QuestMember>) {
        val quest = Quest(tracker, definition)

        members.forEach { quest.addMember(it) }

        quest.start()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : MemberRelatedEvent> fireMission(type: MissionType<T>, context: T) {
        tracker.trackingQuests.forEach { quest ->
            quest.missions
                .asSequence()
                .filter { it.type.isInstanceOf(type) }
                .map { it as Mission<T> }
                .filter { it.status == Mission.Status.STARTED }
                .filter { it.filter(context) }
                .filter { context.members.any { quest.members.contains(it) } }
                .toList()
                .forEach { mission ->
                    mission.fire()
                }
        }
    }
}