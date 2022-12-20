package ru.nsu.editor.view

import javafx.scene.text.Font
import ru.nsu.lib.Library
import tornadofx.*

class MainMenuView : View() {

    override val root = vbox {
        button("Tower editor").action {
            replaceWith<TowerEditorView>()
        }
        button("QUIT").action {
            close()
        }
    }
}
