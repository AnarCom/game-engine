package ru.nsu.engine.engine.entity

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import ru.nsu.engine.engine.Engine
import ru.nsu.engine.util.parsePath
import ru.nsu.lib.common.TowerData
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Tower(
    private val towerConfig: TowerData,
    val towerPosition: Position,
    private val towerLevelImage: ImageView,
    objectId: Int = 0,
    canBeDeleted: Boolean = false,
) : Entity(objectId, canBeDeleted) {
    private var activeUpdate: Int = 0
    private var counter = 0

    init {
        towerLevelImage.image = Image(
            parsePath(towerConfig.file)
        )
        moneyAtDeletion = getActiveUpdate().removeMoneyCashback
    }

    private fun getEnemiesForAttack(
        centerLocation: Pair<Double, Double>,
        engine: Engine
    ): List<Enemy> {

        return engine.getEnemies().asSequence().map {
            Pair(it, it.getPosition())
        }.map {
            Pair(
                it.first, sqrt(
                    (centerLocation.first - it.second.first).pow(2.0) +
                            (centerLocation.second - it.second.second).pow(2.0)
                )
            )
        }.filter {
            it.second < 80.0
        }.map {
            it.first
        }.toList()

    }

    override fun tickHandler(engine: Engine) {
        counter++
        if (counter > getActiveUpdate().shootingSpeed) {
            counter = 0
            val centerLocation = Pair(
                abs(towerLevelImage.x + (towerLevelImage.x + towerLevelImage.fitWidth)) / 2,
                abs(towerLevelImage.y + (towerLevelImage.y + towerLevelImage.fitWidth)) / 2
            )
            val tmp = getEnemiesForAttack(centerLocation, engine)
            val activeUpdate = getActiveUpdate()
            tmp.forEach {
                engine.spawnShell(
                    centerLocation,
                    it,
                    activeUpdate.enemyDamage
                )
            }
        }

    }

    override fun onDelete() {
        towerLevelImage.image = Image("empty.png")
    }

    fun getActiveUpdate() = towerConfig.upgrades[activeUpdate]
    fun canBeUpdated() = (towerConfig.upgrades.size - 1) > activeUpdate
    fun update(): Boolean {
        if (canBeUpdated()) {
            activeUpdate++
            moneyAtDeletion = getActiveUpdate().removeMoneyCashback
            return true
        }
        return false
    }

    fun getUpdateCost(): Int =
        if (!canBeUpdated()) {
            -1
        } else {
            towerConfig.updates[
                activeUpdate + 1
            towerConfig.upgrades[
                    activeUpdate + 1
            ].cost
        }
}
