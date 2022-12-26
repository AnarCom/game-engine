package ru.nsu.editor.view.component

import javafx.geometry.Insets
import javafx.scene.layout.VBox
import ru.nsu.editor.view.utils.*
import ru.nsu.lib.common.TowerData
import ru.nsu.lib.common.TowerUpdate
import tornadofx.*

class TowerSettingsComponent(
    override val preset: Pair<String, TowerData> = Pair(
        defaultString,
        TowerData(
            defaultContentUrl,
            mutableListOf(
                TowerUpdate(0, 0, 0, 0, 0, 0)
            )
        )
    )
) : NamedSettingsComponent<TowerData>("TowerSettingsComponent") {
    private var towerUpgradeComponents = mutableListOf<TowerUpgradeComponent>()
    private var upgradeStack: VBox = vbox {}

    private var towerSprite = textfield { text = data.file }
    private var name = textfield { text = filename }

    init {
        data.upgrades.forEachIndexed {idx, value ->
            val new = TowerUpgradeComponent(idx, value)
            towerUpgradeComponents.add(new)
            upgradeStack.add(new)
        }
    }

    override fun getSettings(): Pair<String, TowerData> {
        return Pair(
            name.text,
            TowerData(
                file = towerSprite.text,
                upgrades = towerUpgradeComponents.map { it.getSettings() }.toMutableList()
            )
        )
    }

    override val root = vbox {
        form {
            fieldset {
                field("Tower name") {
                    add(name)
                }
            }
        }
        squeezebox {
            fold("Visuals", expanded = true) {
                form {
                    fieldset {
                        field("Tower sprite") {
                            add(towerSprite)
                        }
                    }
                }
            }
        }
        scrollpane {
            add(upgradeStack)
            isFitToWidth = true
        }
        borderpane {
            left = hbox {
                button("Add upgrade") {
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
            right = hbox {
                button("Remove") {
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
