package ru.nsu.engine.engine.entity

import javafx.scene.image.ImageView
import ru.nsu.engine.engine.Engine
import ru.nsu.engine.util.parsePath
import ru.nsu.lib.common.EnemyPathPoint
import ru.nsu.lib.common.EnemyType

class Enemy(
    val enemyType: EnemyType,
    val enemyPath: Array<EnemyPathPoint>,
    var hp: Int = 10
) : Entity() {
    val imageView: ImageView = ImageView(
        parsePath(enemyType.file)
    )

    init {
        imageView.fitHeight = 40.0
        imageView.fitWidth = 40.0
        healthAtDeletion = enemyType.enemyAttack
    }

    fun decreaseHp(damage: Int) {
        synchronized(this) {
            hp -= damage
            if (hp <= 0) {
                healthAtDeletion = 0
                moneyAtDeletion = enemyType.gold
                canBeDeleted = true
            }
        }
    }

    override fun tickHandler(engine: Engine) {
    }

    fun getPosition(): Pair<Double, Double> = Pair(imageView.translateX, imageView.translateY)

    fun pathEndEventHandler() {
        canBeDeleted = true
    }
}
