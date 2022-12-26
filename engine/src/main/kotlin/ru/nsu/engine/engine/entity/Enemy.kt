package ru.nsu.engine.engine.entity

import javafx.scene.image.ImageView
import ru.nsu.engine.engine.Engine
import ru.nsu.engine.util.parsePath
import ru.nsu.lib.common.EnemyPathPoint
import ru.nsu.lib.common.EnemyType

class Enemy(
    val enemyType: EnemyType,
    val enemyPath: Array<EnemyPathPoint>
) : Entity() {
    val imageView: ImageView = ImageView(
        parsePath(enemyType.file)
    )
    var animationId:Int = 0

    init {
//        imageView.x = enemyPath[0].x.toDouble()
//        imageView.y = enemyPath[0].y.toDouble()
        imageView.fitHeight = 40.0
        imageView.fitWidth = 40.0
    }

    override fun tickHandler(engine: Engine) {

    }

    fun pathEndEventHandler() {
        canBeDeleted = true
    }
}
