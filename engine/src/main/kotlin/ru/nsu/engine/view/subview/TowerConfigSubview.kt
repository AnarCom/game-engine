package ru.nsu.engine.view.subview

import javafx.scene.Parent
import ru.nsu.lib.common.TowerUpdate
import tornadofx.*

class TowerConfigSubview : View() {

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
                        }
                    }
                }
            }
        }
        isVisible = false
    }
    fun showTowerConfig(towerUpdate: TowerUpdate){
        maxEnemyCount.text = towerUpdate.maxEnemyCount.toString()
        radius.text = towerUpdate.radius.toString()
        shootingSpeed.text = towerUpdate.shootingSpeed.toString()
        towerDamage.text = towerUpdate.enemyDamage.toString()
        removeMoneyCashback.text = towerUpdate.removeMoneyCashback.toString()
        show()
    }
    fun hide(){
        this.root.isVisible = false
    }
    fun show() {
        this.root.isVisible = true
    }
}
