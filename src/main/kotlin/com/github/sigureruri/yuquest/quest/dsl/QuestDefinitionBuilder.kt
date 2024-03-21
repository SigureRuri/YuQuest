package com.github.sigureruri.yuquest.quest.dsl

import com.github.sigureruri.yuquest.quest.definition.QuestDefinition
import com.github.sigureruri.yuquest.util.YuId
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class QuestDefinitionBuilder(val id: YuId, private val block: QuestDefinitionDSL.() -> Unit) : ReadOnlyProperty<Any, QuestDefinition> {
    override fun getValue(thisRef: Any, property: KProperty<*>): QuestDefinition {
        return QuestDefinitionDSL(id).apply(block).toQuestDefinition()
    }
}

fun quest(id: YuId, block: (QuestDefinitionDSL.() -> Unit)): QuestDefinitionBuilder {
    return QuestDefinitionBuilder(id, block)
}