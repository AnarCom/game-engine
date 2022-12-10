package ru.nsu.engine.engine.entity

import ru.nsu.engine.engine.Engine

abstract class Entity(
    var objectId: Int = 0,
    var canBeDeleted: Boolean = false,
) {
    var moneyAtDeletion: Int = 0
        protected set

    var healthAtDeletion: Int = 0
        protected set

    abstract fun tickHandler(engine: Engine)
    fun delete() {
        canBeDeleted = true
        onDelete()
    }

    open fun onDelete() {

    }
}