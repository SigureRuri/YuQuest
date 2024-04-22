package com.github.sigureruri.yuquest.quest

import com.github.sigureruri.yuquest.YuQuest
import com.github.sigureruri.yuquest.quest.bukkit.MissionTypeInitializer
import com.github.sigureruri.yuquest.quest.definition.QuestDefinition
import com.github.sigureruri.yuquest.quest.finalizedhistory.FinalizedHistoryAccessor
import com.github.sigureruri.yuquest.quest.missiontype.MemberRelatedEvent
import com.github.sigureruri.yuquest.quest.missiontype.MissionType
import com.github.sigureruri.yuquest.quest.persistence.finalizedhistory.FinalizedHistoryPersistenceOperator
import com.github.sigureruri.yuquest.quest.persistence.quest.QuestPersistenceOperator

class QuestManager(private val plugin: YuQuest) {
    private var isEnabled = false

    val resourceManager = QuestResourceRepository()

    private val tracker = QuestTracker()

    private val persistenceOperator = QuestPersistenceOperator(plugin, resourceManager, tracker)

    private val historyOperator = FinalizedHistoryPersistenceOperator(plugin)

    val finalizedHistoryAccessor: FinalizedHistoryAccessor
        get() = historyOperator

    fun enable() {
        require(plugin.isEnabled)
        require(!isEnabled)
        isEnabled = true

        plugin.server.pluginManager.registerEvents(MissionTypeInitializer(this), plugin)
        persistenceOperator.enable()
    }

    val trackingQuests: Set<Quest>
        get() = tracker.trackingQuests

    fun start(definition: QuestDefinition) {
        val quest = Quest(tracker, historyOperator, definition)
        quest.start()
    }

    fun startWith(definition: QuestDefinition, members: Set<QuestMember>) {
        val quest = Quest(tracker, historyOperator, definition)

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
                    mission.fire(context)
                }
        }
    }
}