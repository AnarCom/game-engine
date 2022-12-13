package ru.nsu.editor.view.component

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.geometry.Insets
import javafx.scene.layout.VBox
import ru.nsu.lib.common.TowerData
import tornadofx.*

class TowerSettingsComponent : SettingsComponent2<TowerData>("TowerSettingsComponent") {//SettingsComponent<TowerData>, View("TowerSettingsComponent")
//    override val titleProperty: StringProperty = SimpleStringProperty("TowerSettingsComponent")
    private var towerUpgradeComponents = mutableListOf<TowerUpgradeComponent>()
    private lateinit var upgradeStack: VBox
    override fun getSettings(): TowerData {
        return TowerData(
            file = towerSprite.text,
            upgrades = towerUpgradeComponents.map { it.getSettings() }.toMutableList()
        )
    }

    private val defaultUrl = "./content"

    private var towerSprite = textfield { text=defaultUrl }

    override val root = vbox {
        squeezebox {
            fold("Visuals", expanded = true){
                form {
                    fieldset {
                        field("Tower sprite"){
                            add(towerSprite)
                        }
                    }
                }
            }
        }
        scrollpane {
            upgradeStack = vbox{
                val new = TowerUpgradeComponent(0)
                towerUpgradeComponents.add(new)
                add(new)
            }
        }
        borderpane{
            left = hbox{
                button("Add upgrade"){
                    action {
                        val newComponent = TowerUpgradeComponent(towerUpgradeComponents.size)
                        towerUpgradeComponents.add(newComponent)
                        upgradeStack.add(newComponent)
                    }
                    hboxConstraints {
                        marginTop = 10.0
                    }
                }
            }
            right = hbox{
                button("Remove"){
                    action {
                        towerUpgradeComponents.removeLast().removeFromParent()
                    }
                    hboxConstraints {
                        margin = Insets(10.0)
                    }
                }
            }
        }
    }
}