package ru.nsu.editor.view

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.scene.control.ListView
import javafx.scene.layout.VBox
import ru.nsu.editor.view.component.*
import ru.nsu.editor.view.enums.*
import ru.nsu.editor.view.utils.*

import tornadofx.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class EditorView : View("Tower editor") {
    private var menuValue = SimpleObjectProperty<LayoutType>()
    private val mapper = jacksonObjectMapper()
    private val jsonFolder = "./configuration/jsons"

    private var settings: NamedSettingsComponent<Any> = TowerSettingsComponent()
        set(value) {
            field = refreshEntity(settings, settingsParent, value)
        }
    private var settingsParent = vbox { add(settings) }

    private fun <T> genListview(arrayList: ArrayList<T>): ListView<T> {
        return listview{
            items.addAll(arrayList)
            onUserSelect {
                settings = menuValue.value.buildSettingsComp(
                    Pair(
                        it.toString(),
                        mapper.readValue(
                            Paths.get(jsonFolder, menuValue.value.toString(), "${it.toString()}.json").toFile(),
                            menuValue.value.getPresetClass().java
                        )
                    )
                )
            }
        }
    }
    private var fileList = genListview<String>(arrayListOf())
        set(value) {
            field = refreshEntity(
                fileList,
                fileListParent,
                value
            )
        }
    private var fileListParent = vbox { add(fileList) }


    private fun getLayoutTypedFiles(layout: LayoutType): ArrayList<String> {
        val filenames: ArrayList<String> = arrayListOf()
        File(
            Paths.get(jsonFolder, layout.name).toUri()
        ).list()?.forEach { filenames.add(getFilenameFromURI(it)) }
        return filenames
    }

    private fun saveSettings(){
        val (name, params) = settings.getSettings()
        Files.createDirectories(Paths.get(jsonFolder, menuValue.value.toString()))
        val filepath = Paths.get(jsonFolder, menuValue.value.toString(), "$name.json").toFile()
        val saveAction = {mapper.writeValue(filepath, params)}
        if(File(filepath.toString()).exists()){
            confirm(
                header = "Override",
                content = "$name file exists. Do you want to override it?",
                actionFn = saveAction
            )
        } else {saveAction()}
    }
    private fun <T: View> refreshEntity(obj: T, parent: VBox, new: T): T {
        obj.removeFromParent()
        parent.add(new)
        return new
    }
    private fun <T: javafx.scene.Node> refreshEntity(obj: T, parent: VBox, new: T): T {
        obj.removeFromParent()
        parent.add(new)
        return new
    }
    init {
        menuValue.value = LayoutType.TOWER

        settings = TowerSettingsComponent()
        fileList = genListview(getLayoutTypedFiles(menuValue.value))

        menuValue.onChange {
            println(menuValue.value)
            println(getLayoutTypedFiles(menuValue.value))

            fileList = genListview(getLayoutTypedFiles(menuValue.value))

            when (it) {
                LayoutType.TOWER -> {
                    settings = TowerSettingsComponent()
                }

//                LayoutType.ENEMY -> {
//                    settings = EnemySettingsComponent()
//                }

                else -> {
                    println("No such when")
                }
            }
        }
        settings = TowerSettingsComponent()
    }

    override val root = borderpane {
        top {
            useMaxWidth = true
            hbox {
                button("Back").action {
                    replaceWith<MainMenuView>()
                }
                button("Save").action {
                    saveSettings()
                }
                button("Import").action {

                }
                combobox(menuValue, FXCollections.observableArrayList(*LayoutType.values()))
            }
        }
        left {
            vbox {
                squeezebox {
                    fold("${menuValue.name} files", expanded = true) {
                        add(fileListParent)
                    }
                    fold("Sprites", expanded = true) {
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
            add(settingsParent)
        }
        center {
            rectangle {
                width = 200.0
                height = 200.0
            }
        }
    }
}
