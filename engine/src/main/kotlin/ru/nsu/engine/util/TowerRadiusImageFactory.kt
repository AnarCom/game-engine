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
    fun produceImage(towerPosition:Position): Image {
        val writableImage = WritableImage(xFinish, yFinish)
        val pixelWriter = writableImage.pixelWriter
//        for (i in 0 until xFinish) {
//            for (j in 0 until yFinish) {
//                pixelWriter.setColor(i, j, Color.color(0.5, 0.5, 0.5, 1.0))
//            }
//        }
        return writableImage
    }
}