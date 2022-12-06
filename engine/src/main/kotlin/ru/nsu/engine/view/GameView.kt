package ru.nsu.engine.view

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import ru.nsu.engine.engine.Engine
import ru.nsu.engine.engine.entity.Position
import ru.nsu.engine.engine.entity.Tower
import ru.nsu.engine.view.state.GameState
import ru.nsu.engine.view.subview.BuildTowerSubview
import ru.nsu.engine.view.subview.TopGameSubview
import ru.nsu.engine.view.subview.TowerConfigSubview
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
    private val towerConfigSubview = TowerConfigSubview()
    private val topSubview = TopGameSubview()
    private val buildTowerSubview: BuildTowerSubview

    var actionOnClick: ActionOnClick = ActionOnClick.NONE
        set(value) {
            buildTowerSubview.canselButton.isDisable = !value.cancelAvailable
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
        buildTowerSubview = BuildTowerSubview(levelConfiguration.towersConfig, this)
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
                add(buildTowerSubview)
                add(towerConfigSubview)
            }
        }

        top {
            hbox {
                add(topSubview)
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
                    topSubview.logError("block already occupied!")
                    return
                }
                if (!levelConfiguration.fieldPartsConfig[levelConfiguration.fieldStructure[y][x]]!!.isBuildAvailable) {
                    topSubview.logError("cannot build on this block")
                    return
                }
                actionOnClick = ActionOnClick.NONE
                engine.registerEntity(
                    Tower(
                        levelConfiguration.towersConfig[
                                buildTowerSubview.selectedTowerType
                        ]!!,
                        Position(x, y),
                        towerLevel[y][x]
                    )
                )
            }

            ActionOnClick.DELETE -> {
                val tower = engine.getTowerFromPosition(x, y)
                if (tower == null) {
                    topSubview.logError("cannot delete tower - there are no tower")
                    return
                }
                actionOnClick = ActionOnClick.NONE
                tower.delete()
            }

            ActionOnClick.NONE -> {
                val tower = engine.getTowerFromPosition(x, y)
                if (tower != null) {
                    val activeUpdate = tower.getActiveUpdate()
                    towerConfigSubview.showTowerConfig(activeUpdate)
                } else {
                    towerConfigSubview.hide()
                }
            }
        }
        topSubview.hideErrorLabel()
    }
}
