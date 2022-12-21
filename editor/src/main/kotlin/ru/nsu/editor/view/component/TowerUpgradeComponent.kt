package ru.nsu.editor.view.component

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import org.w3c.dom.Node
import ru.nsu.editor.view.utils.defaultInt
import ru.nsu.editor.view.utils.defaultString
import ru.nsu.lib.common.TowerUpdate
import tornadofx.*
import java.awt.TextField
import java.lang.NumberFormatException

class TowerUpgradeComponent(
    index: Int,
    override val preset: TowerUpdate = TowerUpdate(0, 0, 0, 0, 0, 0)
) : SettingsComponent<TowerUpdate>("TowerSettingsComponent") {
    override fun getSettings(): TowerUpdate {
        try {
            return TowerUpdate(
                enemyDamage = damage.text.toInt(),
                shootingSpeed = speed.text.toInt(),
                maxEnemyCount = maxEnemyCount.text.toInt(),
                radius = radius.text.toInt(),
                cost = cost.text.toInt(),
                removeMoneyCashback = sell.text.toInt()
            )
        } catch (e: NumberFormatException) {
            warning("Неверный формат данных", content = e.message)
        }
        return TowerUpdate(0, 0, 0, 0, 0, 0)
    }


    private var damage = textfield { text = preset.enemyDamage.toString() }
    private var speed = textfield { text = preset.shootingSpeed.toString() }
    private var radius = textfield { text = preset.radius.toString() }
    private var maxEnemyCount = textfield { text = preset.maxEnemyCount.toString() }
    private var cost = textfield { text = preset.cost.toString() }
    private var sell = textfield { text = preset.removeMoneyCashback.toString() }

    override val root = vbox {
        borderpane {
            left = hbox {
                text("Upgrade #$index") {
                    hboxConstraints {
                        marginTopBottom(15.0)
                    }
                }
            }
        }
        squeezebox {//TODO move to commponent
            fold("Attack", expanded = false) {
                form {
                    fieldset {
                        field("Damage") {
                            add(damage)
                        }
                        field("Speed") {
                            add(speed)
                        }
                        field("Radius") {
                            add(radius)
                        }
                        field("Max enemy to hit") {
                            add(maxEnemyCount)
                        }
                    }
                }
            }
            fold("Economy", expanded = false) {
                form {
                    fieldset {
                        if (index == 0) {
                            field("Initial") {
                                add(cost)
                            }
                        } else {
                            field("Upgrade") {
                                add(cost)
                            }
                        }
                        field("Sell") {
                            add(sell)
                        }

                    }
                }
            }
        }
    }
}
