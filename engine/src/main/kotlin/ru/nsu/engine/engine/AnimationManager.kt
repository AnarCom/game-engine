package ru.nsu.engine.engine

import javafx.animation.Interpolator
import javafx.animation.PathTransition
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.shape.LineTo
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import javafx.util.Duration
import ru.nsu.engine.engine.entity.Enemy
import tornadofx.*

class AnimationManager(
    private val parent: Node,
) {

    private val map: MutableMap<Enemy, PathTransition> = mutableMapOf()

    fun addEnemyAtAnimationPath(
        enemy: Enemy,
        endPathCallback: () -> Unit
    ) {
        synchronized(this) {
            Platform.runLater {
                parent.add(enemy.imageView)
                val pathForAmination = Path()
                val moveTo = MoveTo(enemy.enemyPath[0].x.toDouble() + 20.0, enemy.enemyPath[0].y.toDouble() + 20.0)
                pathForAmination.elements.add(moveTo)
                for (currentPath in enemy.enemyPath.slice(1 until enemy.enemyPath.size)) {
                    val line =
                        LineTo(currentPath.x.toDouble() + 20.0, currentPath.y.toDouble() + 20.0)
                    pathForAmination.elements.add(line)
                }
                val pathTransition =
                    PathTransition(
                        Duration.millis(((enemy.enemyPath.size - 1) * enemy.enemyType.speed).toDouble()),
                        pathForAmination
                    )
                pathTransition.node = enemy.imageView
                pathTransition.interpolator = Interpolator.LINEAR
                pathTransition.orientation = PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT
                pathTransition.onFinishedProperty().set(EventHandler {
                    endPathCallback()
                })
                pathTransition.play()
                map[enemy] = pathTransition
            }
        }
    }

    fun deleteFromAnimationPath(
        enemy: Enemy
    ) {
        synchronized(this) {
            val imageView = enemy.imageView
            val pathTransitionForEnemy = map[enemy]!!
            Platform.runLater {
                if (imageView.parent != null) {
                    imageView.hide()
                    imageView.removeFromParent()
                    pathTransitionForEnemy.stop()
                }
            }
            map.remove(enemy)
        }
    }

    fun spawnShell(
        from: Pair<Double, Double>,
        to: Enemy,
        damage: Int,
        speed : Double = 300.0,
        image : Image = Image("file:./configuration/content/shell.png")
    ) {
        Platform.runLater{
            val pathForAmination = Path()
            val moveTo = MoveTo(from.first, from.second)
            pathForAmination.elements.add(moveTo)
            val line =
                LineTo(to.getPosition().first + 20.0, to.getPosition().second + 20.0)
            pathForAmination.elements.add(line)
            val pathTransition =
                PathTransition(
                    Duration.millis(speed),
                    pathForAmination
                )
            val imageView = ImageView(
                image
            )
            parent.add(imageView)
            pathTransition.node = imageView
            pathTransition.interpolator = Interpolator.LINEAR
            pathTransition.orientation = PathTransition.OrientationType.NONE
            pathTransition.onFinishedProperty().set(EventHandler {
                to.decreaseHp(damage)
                imageView.hide()
                imageView.removeFromParent()
            })
            pathTransition.play()
        }
    }
}