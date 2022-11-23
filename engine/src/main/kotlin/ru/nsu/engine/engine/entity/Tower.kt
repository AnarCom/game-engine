package ru.nsu.engine.engine.entity

import ru.nsu.engine.engine.Engine
import ru.nsu.lib.common.TowerData

class Tower(
    objectId: Int = 0,
    canBeDeleted: Boolean = false,
    private var towerData: TowerData,
) : Entity(objectId, canBeDeleted) {
    override fun tickHandler(engine: Engine) {
        TODO("Not yet implemented")
    }

}