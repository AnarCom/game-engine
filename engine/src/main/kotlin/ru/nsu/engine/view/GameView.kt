package ru.nsu.engine.view

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import ru.nsu.engine.engine.Engine
import ru.nsu.engine.engine.entity.Position
import ru.nsu.engine.engine.entity.Tower
import ru.nsu.engine.view.state.GameState
import ru.nsu.engine.view.util.ActionOnClick
import ru.nsu.lib.LevelConfiguration
import tornadofx.*
import java.io.File

class GameView : View("My View") {
    private val levelConfiguration: LevelConfiguration
    private val baseField: Array<Array<ImageView>>
    private val towerLevel: Array<Array<ImageView>>
    private val engine: Engine = Engine()

    // user window state
    private var selectedTowerType: String = ""
    private val errorLabel: Label = label("") {
        textFill = c("red")
    }
    private val canselButton: Button = button("cancel") {
        isDisable = true
        action {
            actionOnClick = ActionOnClick.NONE
        }
    }

    private var actionOnClick: ActionOnClick = ActionOnClick.NONE
        set(value) {
            canselButton.isDisable = !value.cancelAvailable
            root.center.cursor = value.cursor
            field = value
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
                    "file:./configuration/content/" + levelConfiguration.fieldPartsConfig[levelConfiguration.fieldStructure[i][j]]!!.file,
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
                    "empty.png", false
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
//        left {
//            button("left")
//        }
        right {
            vbox {
                for (i in levelConfiguration.towersConfig.keys) {
                    button("Построить $i") {
                        action {
                            selectedTowerType = i
                            actionOnClick = ActionOnClick.BUILD
                        }
                    }
                }
                button("delete tower") {
                    action {
                        actionOnClick = ActionOnClick.DELETE
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
        when (actionOnClick) {
            ActionOnClick.BUILD -> {
                if (engine.getTowerFromPosition(x, y) != null) {
                    errorLabel.text = "block already occupied!"
                    return
                }
                if (!levelConfiguration.fieldPartsConfig[levelConfiguration.fieldStructure[y][x]]!!.isBuildAvailable) {
                    errorLabel.text = "cannot build on this block"
                    return
                }
                actionOnClick = ActionOnClick.NONE
                engine.registerEntity(
                    Tower(
                        levelConfiguration.towersConfig[selectedTowerType]!!,
                        Position(x, y),
                        towerLevel[y][x]
                    )
                )
            }

            ActionOnClick.DELETE -> {
                val tower = engine.getTowerFromPosition(x, y)
                if (tower == null) {
                    errorLabel.text = "cannot delete tower - there are no tower"
                    return
                }
                actionOnClick = ActionOnClick.NONE
                tower.delete()
            }

            ActionOnClick.NONE -> {}
        }
        errorLabel.text = ""
    }
}
