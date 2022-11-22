package ru.nsu.engine.view

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import javafx.scene.Cursor
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import ru.nsu.engine.view.state.GameState
import ru.nsu.lib.LevelConfiguration
import tornadofx.*
import java.io.File

class GameView : View("My View") {
    private val levelConfiguration: LevelConfiguration
    private val baseField: Array<Array<ImageView>>
    private val towerLevel: Array<Array<ImageView>>

    init {
        val mapper = jacksonObjectMapper()
        levelConfiguration = mapper.readValue(
            File(
                "./configuration/levels_configs/${GameState.levelData!!.config}"
            )
        )
        baseField = (0 until levelConfiguration.fieldStructure.size).map { i ->
            (0 until levelConfiguration.fieldStructure[i].size).map { j ->
                imageview(
                    "file:./configuration/content/" +
                            levelConfiguration.fieldPartsConfig[
                                    levelConfiguration.fieldStructure[i][j]
                            ]!!.file,
                    false
                ) {
                    fitWidth = levelConfiguration.cellSize.width.toDouble()
                    fitHeight = levelConfiguration.cellSize.height.toDouble()
                    x = (levelConfiguration.cellSize.width * j).toDouble()
                    y = (levelConfiguration.cellSize.height * i).toDouble()
                    toBack()
                    setOnMouseClicked {
                        mouseClickFieldHandler(it, j, i)
                    }
                }
            }.toTypedArray()
        }.toTypedArray()
        val road = "road_1.png"

        towerLevel = (0 until levelConfiguration.fieldStructure.size).map { i ->
            (0 until levelConfiguration.fieldStructure[i].size).map { j ->
                imageview(
                    "empty.png",
                    false
                ) {
                    fitWidth = levelConfiguration.cellSize.width.toDouble()
                    fitHeight = levelConfiguration.cellSize.height.toDouble()
                    x = (levelConfiguration.cellSize.width * j).toDouble()
                    y = (levelConfiguration.cellSize.height * i).toDouble()

                    setOnMouseClicked {
                        mouseClickFieldHandler(it, j, i)
                    }
                }
            }.toTypedArray()
        }.toTypedArray()

    }

    override val root = borderpane {
        center = stackpane {
            group {
                baseField.forEach {
                    it.forEach { image ->
                        this.add(image)
                    }
                }
                towerLevel.forEach { t ->
                    t.forEach { image ->
                        this.add(image)
                        image.toFront()
                    }
                }
            }
        }
        left {
            button("left")
        }
        right {
            vbox {
                for(i in levelConfiguration.towersConfig.keys){
                    button("Построить $i"){
                        action {
                            println(i)
                        }
                    }
                }
            }
        }

        top {
            button("top")
        }
    }

    private fun mouseClickFieldHandler(
        mouseButton: MouseEvent, x: Int, y: Int
    ) {
        if (mouseButton.button == MouseButton.PRIMARY) {
            click(x, y)
        }
    }

    private fun click(x: Int, y: Int) {
        towerLevel[y][x].image = Image("file:./configuration/content/tower_1.png")
        println("$x $y")
    }
}
