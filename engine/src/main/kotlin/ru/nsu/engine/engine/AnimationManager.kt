package ru.nsu.engine.engine

import javafx.animation.Timeline
import javafx.application.Platform
import javafx.scene.Node
import javafx.scene.image.ImageView
import ru.nsu.lib.common.EnemyPathPoint
import ru.nsu.lib.common.EnemyType
import tornadofx.*

class AnimationManager(
    private val parent: Node,
) {

    private val map: MutableMap<Int, ImageView> = mutableMapOf()


    private var counter = 0
    fun addAtAnimationPath(
        image: ImageView,
        enemyConfig: EnemyType,
        path: Array<EnemyPathPoint>,
        endPathCallback: ()-> Unit
    ): Int {
        synchronized(map) {
            val id = counter++
            Platform.runLater {
                parent.add(image)
//                println("${path[0].x.toDouble()} ${path[0].y.toDouble()}")
//                image.x = path[0].x.toDouble()
//                image.y = path[0].y.toDouble()
            }
            map[id] = image

//            val transition = PathTransition()
            return id
        }
    }

    fun deleteFromAnimationPath(
        id: Int
    ) {
        synchronized(map) {
            val imageView = map[id]!!
            Platform.runLater {
                imageView.hide()
                imageView.removeFromParent()
            }

            map.remove(id)
        }
    }
}