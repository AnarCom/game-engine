package ru.nsu.engine.util

import javafx.scene.image.Image
import javafx.scene.image.WritableImage
import ru.nsu.engine.engine.entity.Position
import ru.nsu.lib.common.Size
import tornadofx.*
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class TowerRadiusImageFactory(
    private val xFinish: Int,
    private val yFinish: Int,
    private val pixelSize: Size
) {
    private fun calculateTopLeftAngleCoor(
        towerPosition: Position
    ): Pair<Int, Int> = Pair(
        towerPosition.x * pixelSize.width,
        towerPosition.y * pixelSize.height
    )

    private fun generateCirclePoints(
        radius: Int,
        centerCoordinates: Pair<Int, Int>
    ) = (0..3600)
        .map {
            it.toDouble() / 10
        }.map {
            Pair(
                (centerCoordinates.first + radius * cos(Math.toRadians(it))).toInt(),
                (centerCoordinates.second + radius * sin(Math.toRadians(it))).toInt()
            )
        }.filter {
            (it.first in 0 until xFinish) && (it.second in 0 until yFinish)
        }.toMutableList()


    fun produceImage(
        towerPosition: Position,
        radius: Int,
    ): Image {
        val topLeftAngleCoordinates = calculateTopLeftAngleCoor(towerPosition)

        // down right angle is top left angle for diagonal cell
        val downRightAngleCoordinates = calculateTopLeftAngleCoor(
            Position(
                x = towerPosition.x + 1,
                y = towerPosition.y + 1
            )
        )


        val centerCoordinates = Pair(
            ((topLeftAngleCoordinates.first + downRightAngleCoordinates.first) / 2),
            ((topLeftAngleCoordinates.second + downRightAngleCoordinates.second) / 2),
        )

        val radiusPx = abs(centerCoordinates.first - topLeftAngleCoordinates.first) + (
                radius * pixelSize.width
                )

        val writableImage = WritableImage(xFinish, yFinish)
        val pixelWriter = writableImage.pixelWriter

        val coordinates = (0 until 3).map {
            generateCirclePoints(radiusPx - it, centerCoordinates)
        }.reduce { acc, pairs ->
            acc.apply {
                this.addAll(pairs)
            }
        }

        for (i in coordinates) {
            pixelWriter.setColor(i.first, i.second, c("black"))
        }
        return writableImage
    }
}