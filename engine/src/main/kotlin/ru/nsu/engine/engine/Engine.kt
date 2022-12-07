package ru.nsu.engine.engine

import javafx.animation.Animation
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.util.Duration
import ru.nsu.engine.engine.entity.Entity
import ru.nsu.engine.engine.entity.Tower

class Engine(
    private val moneyHandler: (moneyValue: Int) -> Unit
) {
    private val entityList: MutableList<Entity> = mutableListOf()
    private var idCounter: Int = 0
    private val timeline = Timeline()

    init {
        timeline.keyFrames.add(KeyFrame(Duration.millis(100.0), { t ->
            deleteRemovableElements()
        }))
        timeline.cycleCount = Animation.INDEFINITE
        timeline.play()
    }

    fun registerEntity(entity: Entity): Int {
        entity.objectId = idCounter
        synchronized(entityList) {
            entityList.add(entity)
        }
        return idCounter++
    }

    fun getTowerFromPosition(x: Int, y: Int): Tower? {
        synchronized(entityList) {
            val lst = entityList
                .filterIsInstance<Tower>()
                .filter {
                    it.towerPosition.x == x && it.towerPosition.y == y
                }

            if (lst.isEmpty()) {
                return null
            } else {
                return lst[0]
            }

        }
    }

    private fun deleteRemovableElements() {
        synchronized(entityList) {
            val toDelete = entityList.filter {
                it.canBeDeleted
            }
            toDelete.forEach {
                entityList.remove(it)
                if(it.moneyAtDeletion != 0){
                    moneyHandler(it.moneyAtDeletion)
                }
            }
        }
    }
}
