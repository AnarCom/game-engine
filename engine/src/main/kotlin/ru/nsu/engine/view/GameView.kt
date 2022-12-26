package ru.nsu.engine.view

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import javafx.scene.Group
import javafx.scene.control.Button
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import ru.nsu.engine.engine.Engine
import ru.nsu.engine.engine.entity.Position
import ru.nsu.engine.engine.entity.Tower
import ru.nsu.engine.util.Wallet
import ru.nsu.engine.util.parsePath
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
    private val engine: Engine
    private val nextWaveButton: Button

    private val circleImage: ImageView
    private val fieldGroup: Group

    // user window state
    private val towerConfigSubview: TowerConfigSubview
    private val topSubview = TopGameSubview()
    private val buildTowerSubview: BuildTowerSubview
    private val wallet: Wallet

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
        buildTowerSubview = BuildTowerSubview(
            levelConfiguration.towersConfig,
            this
        )

        baseField = (0 until levelConfiguration.fieldStructure.size).map { i ->
            (0 until levelConfiguration.fieldStructure[i].size).map { j ->
                imageview(
                    parsePath(levelConfiguration.fieldPartsConfig[levelConfiguration.fieldStructure[i][j]]!!.file),
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
        circleImage = imageview {
            x = 0.0
            y = 0.0
            fitHeight =
                (levelConfiguration.cellSize.height * levelConfiguration.fieldStructure.size)
                    .toDouble()

            fitWidth =
                (levelConfiguration.cellSize.width * levelConfiguration.fieldStructure[0].size)
                    .toDouble()
        }

        wallet = topSubview.wallet
        wallet.addMoney(levelConfiguration.startMoney)

        towerConfigSubview = TowerConfigSubview(
            circleImage,
            levelConfiguration.cellSize,
            wallet
        )
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

        fieldGroup = group {
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
            add(circleImage)
            circleImage.toFront()
        }

        engine = Engine(
            levelConfiguration.enemyConfig,
            levelConfiguration.cellSize,
            {
                wallet.addMoney(it)
            },
            {
                println(it)
            }
        )

        nextWaveButton = button("next wave") {
            action {
                this.isDisable = true
                engine.startWave(
                    {
                        if (engine.isNextWaveAvailable()) {
                            this.isDisable = false
                        }
                    },
                    levelConfiguration.enemyConfig.enemyPath
                )
            }
        }

    }

    override val root = borderpane {
        center = stackpane {
            add(fieldGroup)
            engine.root = fieldGroup
        }
        right {
            vbox {
                add(buildTowerSubview)
                add(nextWaveButton)
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
                if (!wallet.writeOffMoneyIfCan(
                        levelConfiguration.towersConfig[
                                buildTowerSubview.selectedTowerType
                        ]!!
                            .upgrades[0]
                            .cost
                    )
                ) {
                    return
                }
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
                towerConfigSubview.hide()
            }

            ActionOnClick.NONE -> {
                val tower = engine.getTowerFromPosition(x, y)
                if (tower != null) {
                    towerConfigSubview.showTowerConfig(tower)
                } else {
                    towerConfigSubview.hide()
                }
            }
        }
        topSubview.hideErrorLabel()
    }
}
