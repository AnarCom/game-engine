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
    init {
        towerLevelImage.image = Image(
            "file:./configuration/content/" +
                    towerConfig.file
        )
    }

    override fun tickHandler(engine: Engine) {
        TODO("Not yet implemented")
    }

    override fun onDelete() {
        towerLevelImage.image = Image("empty.png")
    }
}
