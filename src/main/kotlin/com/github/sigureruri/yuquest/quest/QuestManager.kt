package com.github.sigureruri.yuquest.quest

import com.github.sigureruri.yuquest.data.persistence.PersistentDataManipulator
import com.github.sigureruri.yuquest.quest.mission.MemberRelatedEvent
import com.github.sigureruri.yuquest.quest.mission.Mission
import com.github.sigureruri.yuquest.quest.mission.MissionType
import com.github.sigureruri.yuquest.quest.persistence.YamlQuestDataManipulator
import java.util.*

class QuestManager {
    val resourceManager = QuestResourceManager()

    private val questDataManipulator: PersistentDataManipulator<UUID, Quest> = YamlQuestDataManipulator()

    private val tracker = QuestTracker()

    val trackingQuests: List<Quest>
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