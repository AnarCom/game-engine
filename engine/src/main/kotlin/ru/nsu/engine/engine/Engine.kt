package ru.nsu.engine.engine

import ru.nsu.engine.engine.entity.Entity

class Engine {
    private val entityList: MutableList<Entity> = mutableListOf()

    private var idCounter: Int = 0
    fun registerEntity(entity: Entity): Int {
        entity.objectId = idCounter
        synchronized(entityList) {
            entityList.add(entity)
        }
        return idCounter++
    }
}
