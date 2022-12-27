package ru.nsu.editor.view.component

import javafx.scene.Parent
import ru.nsu.editor.view.utils.defaultContentUrl
import ru.nsu.editor.view.utils.defaultString
import ru.nsu.lib.common.FieldImageInfo
import tornadofx.*

class TileSettingsComponent (
    override val preset: Pair<String, FieldImageInfo> = Pair (
        defaultString,
        FieldImageInfo(
            defaultContentUrl,
            false
        )
    )
) : NamedSettingsComponent<FieldImageInfo>("TileSettingsComponent") {
    override fun getSettings(): Pair<String, FieldImageInfo> {
        return Pair(
            name.text,
            FieldImageInfo(
                sprite.text,
                canPlace.isSelected
            )
        )
    }

    private val name = textfield { text = filename }
    private val sprite = textfield { text = data.file }
    private val canPlace = checkbox {
        isSelected = data.isBuildAvailable
    }

    override val root = vbox {
        form {
            fieldset {
                field("Tile name") {
                    add(name)
                }
                field("Sprite") {
                    add(sprite)
                }
                field("Can place towers"){
                    add(canPlace)
                }
            }
        }
    }
}