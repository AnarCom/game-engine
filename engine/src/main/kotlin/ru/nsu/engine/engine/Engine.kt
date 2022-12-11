package ru.nsu.engine.engine

import javafx.animation.Animation
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.scene.Node
import javafx.util.Duration
import ru.nsu.engine.engine.entity.Enemy
import ru.nsu.engine.engine.entity.Entity
import ru.nsu.engine.engine.entity.Tower
import ru.nsu.lib.common.*

class Engine(
    private val enemyConfig: EnemyConfig,
    private val cellSize: Size,
    private val moneyHandler: (moneyValue: Int) -> Unit,
    private val healthHandler: (heathChangeValue: Int) -> Unit
) {
    private val entityList: MutableList<Entity> = mutableListOf()
    private var idCounter: Int = 0
    private val timeline = Timeline()
    var root: Node?= null
        set(value) {
            animationManager = AnimationManager(
                value!!
            )
            field = value
        }
    private var animationManager:AnimationManager? = null

    init {
        timeline.keyFrames.add(KeyFrame(Duration.millis(100.0), { t ->
            deleteRemovableElements()
            generateTick()
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
        if (entity is Enemy) {
            animationManager!!.addAtAnimationPath(
                entity.imageView,
                entity.enemyType,
                entity.enemyPath
            ) {
                entity.pathEndEventHandler()
            }

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
                if (it.moneyAtDeletion != 0) {
                    moneyHandler(it.moneyAtDeletion)
                }
                if (it.healthAtDeletion != 0) {
                    healthHandler(it.healthAtDeletion)
                }
            }
            toDelete
                .filterIsInstance(Enemy::class.java)
                .forEach {
                    animationManager!!.deleteFromAnimationPath(
                        it.animationId
                    )
                }
//            if (toDelete.isNotEmpty()) {
//                println(toDelete.size)
//            }
        }
    }

    private fun generateTick() {
        synchronized(entityList) {
            entityList.forEach {
                it.tickHandler(this)
            }
        }
    }

    private var actualWaveInd: Int = 0
    fun isNextWaveAvailable() = actualWaveInd < enemyConfig.enemyWaves.size
    fun startWave(
        waveIsDoneCallback: () -> Unit,
        path: Array<EnemyPathPoint>
    ): Thread {
        val factoryTread = Thread(
            WaveFactory(
                enemyConfig.enemyWaves[actualWaveInd],
                this,
                waveIsDoneCallback,
                path.map {
                    EnemyPathPoint(
                        it.x * cellSize.width,
                        it.y * cellSize.height
                    )
                }.toTypedArray(),
                enemyConfig.enemyTypes
            )
        )
        factoryTread.start()
        actualWaveInd++
        return factoryTread
    }

    private class WaveFactory(
        private val enemyWave: EnemyWave,
        private val engine: Engine,
        private val waveIsDoneCallback: () -> Unit,
        private val enemyPathPoint: Array<EnemyPathPoint>,
        private val enemies: Map<String, EnemyType>
    ) : Runnable {
        override fun run() {
            for (i in enemyWave.wave) {
                engine.registerEntity(
                    Enemy(
                        enemies[i]!!,
                        enemyPathPoint
                    )
                )
                Thread.sleep(enemyWave.betweenDelayMs.toLong())
            }
            waveIsDoneCallback()
        }

    }
}
