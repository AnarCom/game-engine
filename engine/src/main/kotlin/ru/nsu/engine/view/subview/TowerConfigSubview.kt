package ru.nsu.engine.view.subview

import javafx.scene.Parent
import javafx.scene.control.Button
import ru.nsu.engine.engine.entity.Tower
import ru.nsu.lib.common.TowerUpdate
import tornadofx.*

class TowerConfigSubview : View() {

    private var updateButton: Button = button("Update") {
        action {
            val tower = activeTower
            tower.update()
            showTowerConfig(tower)
        }
    }

    private lateinit var activeTower: Tower

    private val radius = textfield {
        isEditable = false
        text = ""
    }

    private val maxEnemyCount = textfield {
        isEditable = false
        text = ""
    }

    private val removeMoneyCashback = textfield {
        isEditable = false
        text = ""
    }

    private val shootingSpeed = textfield {
        isEditable = false
        text = ""
    }

    private val towerDamage = textfield {
        isEditable = false
        text = ""
    }

    override val root: Parent = borderpane {
        center = hbox {
            squeezebox {
                fold("Characteristics", expanded = true) {
                    form {
                        fieldset {
                            field("radius") {
                                add(radius)
                            }
                            field("max enemy attack count") {
                                add(maxEnemyCount)
                            }
                            field("sell cost") {
                                add(removeMoneyCashback)
                            }
                            field("shooting speed") {
                                add(shootingSpeed)
                            }
                            field("damage") {
                                add(towerDamage)
                            }
                            field("update") {
                                add(updateButton)
                            }
                        }
                    }
                }
            }
        }
        isVisible = false
    }

    private fun showTowerConfig(towerUpdate: TowerUpdate) {
        maxEnemyCount.text = if (towerUpdate.maxEnemyCount == -1) {
            "no"
        } else {
            towerUpdate.maxEnemyCount.toString()
        }

        radius.text = towerUpdate.radius.toString()
        shootingSpeed.text = towerUpdate.shootingSpeed.toString()
        towerDamage.text = towerUpdate.enemyDamage.toString()
        removeMoneyCashback.text = towerUpdate.removeMoneyCashback.toString()
        showUpdateButton()
        show()
    }

    fun showTowerConfig(tower: Tower) {
        activeTower = tower
        showTowerConfig(tower.getActiveUpdate())
    }

    private fun showUpdateButton() {
        val canBeUpdated = activeTower.canBeUpdated()
        updateButton.isDisable = !canBeUpdated
        if (canBeUpdated) {
            updateButton.text = "Update (-${activeTower.getUpdateCost()}$)"
        } else {
            updateButton.text = "Max update"
        }
    }

    fun hide() {
        this.root.isVisible = false
    }

    fun show() {
        this.root.isVisible = true
    }
}
