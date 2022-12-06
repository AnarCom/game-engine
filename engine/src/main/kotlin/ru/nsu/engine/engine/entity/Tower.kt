package ru.nsu.engine.engine.entity

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import ru.nsu.engine.engine.Engine
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
            "file:./configuration/content/" +
                    towerConfig.file
        )
        moneyAtDeletion = getActiveUpdate().removeMoneyCashback
    }

    override fun tickHandler(engine: Engine) {
        TODO("Not yet implemented")
    }

    override fun onDelete() {
        towerLevelImage.image = Image("empty.png")
    }

    fun getActiveUpdate() = towerConfig.updates[activeUpdate]
    fun canBeUpdated() = (towerConfig.updates.size - 1) > activeUpdate
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
            ].cost
        }
}
