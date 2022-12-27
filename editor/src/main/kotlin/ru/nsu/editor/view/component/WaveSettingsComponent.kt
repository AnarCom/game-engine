package ru.nsu.editor.view.component

import javafx.geometry.Insets
import javafx.scene.Parent
import javafx.scene.control.ComboBox
import ru.nsu.editor.view.enums.LayoutType
import ru.nsu.editor.view.utils.defaultString
import ru.nsu.editor.view.utils.getFilenames
import ru.nsu.editor.view.utils.jsonFolder
import ru.nsu.lib.common.EnemyWave
import tornadofx.*
import java.nio.file.Paths

class WaveSettingsComponent (
    override val preset: Pair<String, EnemyWave> = Pair(
        defaultString,
        EnemyWave(
            0,
            arrayOf()
        )
    )
) : NamedSettingsComponent<EnemyWave>("WavedSettingsComponent") {
    private val enemyFiles = getFilenames(Paths.get(jsonFolder, LayoutType.ENEMY.name).toUri())

    override fun getSettings(): Pair<String, EnemyWave> {
        val enemyArray: Array<String> = Array(enemyComboboxList.size){""}
        enemyComboboxList.forEachIndexed { index, comboBox ->
            enemyArray[index] = comboBox.value
        }
        println(enemyArray)
        return Pair(
            name.text,
            EnemyWave(
                delay.text.toInt(),
                enemyArray
            )
        )
    }


    private var name = textfield { text = filename }
    private var delay = textfield { text = data.betweenDelayMs.toString() }
    private var enemyComboboxList = arrayListOf<ComboBox<String>>()
    private var enemyStack = vbox{
        data.wave.forEach {
            val newEl = combobox(
                values = enemyFiles
            ){
                value = it
                vboxConstraints {
                    margin = Insets(10.0)
                }
            }
            enemyComboboxList.add(newEl)
            add(newEl)
        }
    }

    override val root = vbox {
        form {
            fieldset {
                field("Wave name") {
                    add(name)
                }
                field("Enemy income delay (ms)"){
                    add(delay)
                }
            }
        }
        scrollpane {
            add(enemyStack)
            isFitToWidth = true
        }
        borderpane {
            left = hbox {
                button("Add enemy") {
                    action {
                        val newComponent = combobox(
                            values = enemyFiles
                        ){
                            vboxConstraints {
                                margin = Insets(10.0)
                            }
                        }
                        enemyComboboxList.add(newComponent)
                        enemyStack.add(newComponent)
                    }
                    hboxConstraints {
                        marginTop = 10.0
                    }
                }
            }
            right = hbox {
                button("Remove") {
                    action {
                        enemyComboboxList.removeLast().removeFromParent()
                    }
                    hboxConstraints {
                        margin = Insets(10.0)
                    }
                }
            }
        }
    }
}