package ru.nsu.engine.engine.entity

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import ru.nsu.engine.engine.Engine
import ru.nsu.engine.util.Common
import ru.nsu.lib.common.TowerData

class Tower(
    private val towerConfig: TowerData,
    val towerPosition: Position,
    private val towerLevelImage: ImageView,
    objectId: Int = 0,
    canBeDeleted: Boolean = false,
) : Entity(objectId, canBeDeleted) {
    private var activeUpdate: Int = 0

    init {
        towerLevelImage.image = Image(
            Common().parsePath(towerConfig.file)
        )
        moneyAtDeletion = getActiveUpdate().removeMoneyCashback
    }

    override fun tickHandler(engine: Engine) {
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
            towerConfig.upgrades[
                    activeUpdate + 1
            ].cost
        }
}
