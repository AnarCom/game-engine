package ru.nsu.editor.view

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.scene.control.ListView
import javafx.scene.layout.VBox
import ru.nsu.editor.view.component.*
import ru.nsu.editor.view.enums.*
import ru.nsu.editor.view.utils.*

import tornadofx.*
import java.io.File
import java.net.URI
import java.nio.file.Files
import java.nio.file.Paths

class EditorView : View("Tower editor") {
    private var menuValue = SimpleObjectProperty<LayoutType>()
    private val mapper = jacksonObjectMapper()

    private var settings: NamedSettingsComponent<Any> = TowerSettingsComponent()
        set(value) {
            field = refreshEntity(settings, settingsParent, value)
        }
    private var settingsParent = vbox { add(settings) }

    private fun genFileListview(arrayList: ArrayList<String>): ListView<String> {
        return listview {
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

    private fun genContentListView(arrayList: ArrayList<String>): ListView<String>{
        return listview {
            items.addAll(arrayList)
            onUserSelect {
                clipboard.putString(Paths.get(defaultContentUrl, it).toString())
            }
        }
    }
    private var fileList = genFileListview(arrayListOf())
        set(value) {
            field = refreshEntity(
                fileList,
                fileListParent,
                value
            )
        }
    private var fileListParent = vbox { add(fileList) }

    private var contentList = genContentListView(arrayListOf())
        set(value){
            field = refreshEntity(
                contentList,
                contentListParent,
                value
            )
        }
    private var contentListParent = vbox { add(contentList) }

    private fun saveSettings() {
        val (name, params) = settings.getSettings()
        Files.createDirectories(Paths.get(jsonFolder, menuValue.value.toString()))
        val filepath = Paths.get(jsonFolder, menuValue.value.toString(), "$name.json").toFile()
        val saveAction = {
            mapper.writeValue(filepath, params)
            fileList = genFileListview(getFilenames(Paths.get(jsonFolder, menuValue.value.name).toUri()))
        }
        if (File(filepath.toString()).exists()) {
            confirm(
                header = "Override",
                content = "$name file exists. Do you want to override it?",
                actionFn = saveAction
            )
        } else {
            saveAction()
        }
    }

    private fun <T : View> refreshEntity(obj: T, parent: VBox, new: T): T {
        obj.removeFromParent()
        parent.add(new)
        return new
    }

    private fun <T : javafx.scene.Node> refreshEntity(obj: T, parent: VBox, new: T): T {
        obj.removeFromParent()
        parent.add(new)
        return new
    }

    init {
        menuValue.value = LayoutType.TOWER

        settings = TowerSettingsComponent()

        /*println(Paths.get(defaultContentUrl).toUri())
        println(getFilenames(Paths.get(defaultContentUrl).toUri()))
        println(Paths.get(jsonFolder, menuValue.value.name).toUri())
        println(getFilenames(Paths.get(jsonFolder, menuValue.value.name).toUri()))*/

        contentList = genContentListView(getFilenames(Paths.get(defaultContentUrl).toUri()))
        fileList = genFileListview(getFilenames(Paths.get(jsonFolder, menuValue.value.name).toUri()))

        menuValue.onChange {
            println(menuValue.value)

            fileList = genFileListview(getFilenames(Paths.get(jsonFolder, menuValue.value.name).toUri()))
            settings = menuValue.value.buildSettingsComp()
        }
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
                button("New").action {
                    settings = menuValue.value.buildSettingsComp()
                }
            }
        }
        left {
            vbox {
                squeezebox {
                    fold("${menuValue.name} files", expanded = true) {
                        add(fileListParent)
                    }
                    fold("Sprites", expanded = true) {
                        add(contentListParent)
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
