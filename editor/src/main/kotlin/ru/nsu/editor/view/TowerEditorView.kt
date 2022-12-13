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
    private var towerUpgradeComponents = mutableListOf<TowerUpgradeComponent>()
    private lateinit var upgradeStack:VBox

    override val root = borderpane {
        top{
            useMaxWidth = true
            hbox{
                button("Back").action {
                    replaceWith<MainMenuView>()
                }
                button("Save").action {

                }
                button("Import").action {

                }
                field("Type"){
                    combobox<String>{//TODO: No field
                        items=FXCollections.observableArrayList("Tower", "Enemy", "")
                    }
                }
            }
        }
        left {
            vbox{
                squeezebox {
                    fold("Tower files", expanded = true){
                        listview<String> {
                            items.add("Alpha")
                            items.add("Beta")
                            items.add("Gamma")
                            items.add("Delta")
                            items.add("Epsilon")
                        }
                    }
                    fold("Sprites", expanded = true){
                        listview<String> {
                            items.add("Alpha")
                            items.add("Beta")
                            items.add("Gamma")
                            items.add("Delta")
                            items.add("Epsilon")
                        }
                    }
                }
            }
        }
        right {
            vbox {
                squeezebox {
                    fold("Visuals", expanded = true){
                        form {
                            fieldset {
                                field("Tower sprite"){
                                    textfield(){

                                    }
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
        center {
            rectangle {
                width=200.0
                height=200.0
            }
        }
    }
}