package ru.nsu.engine.util

import javafx.scene.image.Image
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color
import ru.nsu.engine.engine.entity.Position
import ru.nsu.lib.common.Size

class TowerRadiusImageFactory(
    private val xFinish: Int,
    private val yFinish: Int,
    private val pixelSize: Size
) {
    fun produceImage(
        towerPosition: Position,
        radius: Int,
    ): Image {
        val xPos = towerPosition.x * pixelSize.width
        val yPos = towerPosition.y * pixelSize.height
        val writableImage = WritableImage(xFinish, yFinish)
        val pixelWriter = writableImage.pixelWriter
        for (i in xPos until (xPos + pixelSize.width)) {
            for (j in yPos until (yPos + pixelSize.height)) {
                pixelWriter.setColor(i, j, Color.color(0.5, 0.5, 0.5, 0.5))
            }
        }
        return writableImage
    }
}