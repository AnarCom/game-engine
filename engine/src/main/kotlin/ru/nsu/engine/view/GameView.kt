package ru.nsu.engine.view

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import javafx.scene.Cursor
import javafx.scene.control.Button
import javafx.scene.control.Label
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
    private val isBlockOccupied: Array<Array<Boolean>>

    // user window state
    private var selectedTowerType: String = ""
    private val errorLabel: Label = label("") {
        textFill = c("red")
    }
    private val canselButton: Button = button("cancel") {
        isDisable = true
        action {
            buildTowerOnClick = false
            deleteTowerOnClick = false
        }
    }
    private var buildTowerOnClick: Boolean = false
        set(value) {
//            deleteTowerOnClick = false
            canselButton.isDisable = !value
            field = value
            root.center.cursor = if (value) {
                Cursor.HAND
            } else {
                Cursor.DEFAULT
            }
        }
    private var deleteTowerOnClick: Boolean = false
        set(value) {
//            buildTowerOnClick = false
            canselButton.isDisable = !value
            field = value
            root.center.cursor = if (value) {
                Cursor.CROSSHAIR
            } else {
                Cursor.DEFAULT
            }
        }

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

        isBlockOccupied = Array(levelConfiguration.fieldStructure.size) {
            Array(levelConfiguration.fieldStructure[it].size) {
                false
            }
        }
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
                for (i in levelConfiguration.towersConfig.keys) {
                    button("Построить $i") {
                        action {
                            println(i)
                            selectedTowerType = i
                            buildTowerOnClick = true
                        }
                    }
                }
                button("delete tower") {
                    action {
                        deleteTowerOnClick = true
                    }
                }
                add(canselButton)
            }
        }

        top {
            hbox {
                add(errorLabel)
            }
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
        if (buildTowerOnClick) {
            if (isBlockOccupied[y][x]) {
                errorLabel.text = "block already occupied!"
                return
            }
            if (
                !levelConfiguration.fieldPartsConfig[
                        levelConfiguration.fieldStructure[y][x]
                ]!!.isBuildAvailable
            ){
                errorLabel.text = "cannot build on this block"
                return
            }


                buildTowerOnClick = false
            towerLevel[y][x].image = Image(
                "file:./configuration/content/" +
                        levelConfiguration.towersConfig[selectedTowerType]!!.file
            )
            isBlockOccupied[y][x] = true
        }
        if (deleteTowerOnClick) {
            if (!isBlockOccupied[y][x]) {
                errorLabel.text = "cannot delete tower - there are no tower"
                return
            }
            deleteTowerOnClick = false
            towerLevel[y][x].image = Image("empty.png")
            isBlockOccupied[y][x] = false
        }
        errorLabel.text = ""
    }
}
