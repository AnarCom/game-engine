package ru.nsu.engine.engine

import javafx.animation.PathTransition
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.image.ImageView
import javafx.scene.shape.LineTo
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import javafx.util.Duration
import ru.nsu.engine.engine.entity.Enemy
import ru.nsu.lib.common.EnemyPathPoint
import ru.nsu.lib.common.EnemyType
import tornadofx.*

class AnimationManager(
    private val parent: Node,
) {

    private val map: MutableMap<Int, ImageView> = mutableMapOf()


    private var counter = 0
    fun addEnemyAtAnimationPath(
        image: ImageView,
        enemyConfig: EnemyType,
        path: Array<EnemyPathPoint>,
        endPathCallback: () -> Unit,
        registerDamage: (damage: Int) -> Unit
    ): Int {
        synchronized(this) {
            val id = counter++
            Platform.runLater {
                parent.add(image)
                val pathForAmination = Path()
                val moveTo = MoveTo(path[0].x.toDouble() + 20.0, path[0].y.toDouble() + 20.0)
                pathForAmination.elements.add(moveTo)
                for (currentPath in path.slice(1 until path.size)) {
                    val line =
                        LineTo(currentPath.x.toDouble() + 20.0, currentPath.y.toDouble() + 20.0)
                    pathForAmination.elements.add(line)
                }
                val pathTransition =
                    PathTransition(
                        Duration.millis(((path.size - 1) * enemyConfig.speed).toDouble()),
                        pathForAmination
                    )
                pathTransition.node = image
                pathTransition.orientation = PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT
                pathTransition.onFinishedProperty().set(EventHandler {
//                    image.removeFromParent()
                    endPathCallback()
                })
                pathTransition.play()
            }
            map[id] = image

//            val transition = PathTransition()
            return id
        }
    }

    fun deleteFromAnimationPath(
        id: Int
    ) {
        synchronized(this) {
            val imageView = map[id]!!
            Platform.runLater {
                if (imageView.parent != null) {
                    imageView.hide()
                    imageView.removeFromParent()
                }
            }

            map.remove(id)
        }
    }

    fun spawnShell(
        from: Pair<Double, Double>,
        to: Enemy,
        damage: Int,
        pathToShellFile:String = "./configuration/content/shell.png"
    ) {
        to.decreaseHp(damage)
    }
}