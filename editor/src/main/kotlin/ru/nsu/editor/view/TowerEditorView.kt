package ru.nsu.editor.view

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.scene.control.ComboBox
import javafx.scene.control.SkinBase
import javafx.scene.layout.VBox
import org.w3c.dom.Node
import ru.nsu.editor.view.component.EnemySettingsComponent
import ru.nsu.editor.view.component.SettingsComponent
import ru.nsu.editor.view.component.TowerSettingsComponent
import ru.nsu.editor.view.component.TowerUpgradeComponent
import ru.nsu.lib.common.TowerData
import ru.nsu.lib.common.TowerUpdate

import tornadofx.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Objects

class TowerEditorView : View("Tower editor") {
    private var menuValue = SimpleObjectProperty<LayoutType>()
    private val mapper = jacksonObjectMapper()
    private val jsonFolder = "./jsons"

    private var settings: SettingsComponent<Any>
    private lateinit var settingsParent: VBox
    private fun refreshSettings(new: SettingsComponent<Any>){
        settings.removeFromParent()
        settings = new
        settingsParent.add(settings)
    }
    init {
        menuValue.value = LayoutType.TOWER
        menuValue.onChange {
            println(menuValue.value)
            when(it){
                LayoutType.TOWER -> refreshSettings(TowerSettingsComponent())
                LayoutType.ENEMY -> refreshSettings(EnemySettingsComponent())
                else -> {
                    println("No such when")
                }
            }
        }
        settings = TowerSettingsComponent()
    }

    override val root = borderpane {
        top{
            useMaxWidth = true
            hbox{
                button("Back").action {
                    replaceWith<MainMenuView>()
                }
                button("Save").action {
                    Files.createDirectories(Paths.get(jsonFolder, "/${menuValue.value}/"))
//                    println(Paths.get(jsonFolder, "/$menuValue/test.json").toFile())
                    mapper.writeValue(Paths.get(jsonFolder, "/${menuValue.value}/test.json").toFile(), settings.getSettings())
                }
                button("Import").action {

                }
                combobox(menuValue, FXCollections.observableArrayList(*LayoutType.values()))
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
            settingsParent = vbox{
                add(settings)
            }
        }
        center {
            rectangle {
                width=200.0
                height=200.0
            }
        }
    }

    private enum class LayoutType{
        NONE, TOWER, ENEMY, MAP, WAVE
    }
}