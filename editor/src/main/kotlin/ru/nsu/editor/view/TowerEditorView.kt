package ru.nsu.editor.view

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.scene.control.SkinBase
import javafx.scene.layout.VBox
import org.w3c.dom.Node
import ru.nsu.editor.view.component.TowerUpgradeComponent
import ru.nsu.lib.common.TowerData
import ru.nsu.lib.common.TowerUpdate

import tornadofx.*
import java.io.File

class TowerEditorView : View("Tower editor") {
    private var towerData = TowerData("",
        mutableListOf(
            TowerUpdate(0, 0, 0, 0, 0, 0)
        )
    )
    private var towerUpgradeComponents = mutableListOf<TowerUpgradeComponent>()
    private lateinit var upgradeStack:VBox

    init {
        val mapper = jacksonObjectMapper()
//        mapper.writeValue(
//            File("./configuration/test.json"),
//            TowerData
//        )
    }

    override val root = borderpane {
        top{
            useMaxWidth = true
            hbox{
                button("Back").action {
                    replaceWith<MainMenuView>()
                }
                button("Save").action {

                }
                button("Import")
            }
        }
        left {
            button("Left")
        }
        right {
            vbox {
                squeezebox {
                    fold("Visuals", expanded = true){
                        form {
                            fieldset {
                                field("Tower sprite"){
                                    textfield(towerData.file)
                                }
                                field("Projectile sprite TBD"){
                                    textfield()
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
//                                towerData.upgrades.add(TowerUpdate(0,0,0,0,0,0)) TODO: pull data from components only on save
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
    }

    enum class TowerType{
        AOE, TARGET
    }
}