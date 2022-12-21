package ru.nsu.editor.view

import javafx.scene.text.Font
import ru.nsu.lib.Library
import tornadofx.*

class MainMenuView : View() {

    override val root = vbox {
        button("Editor").action {
            replaceWith<EditorView>()
        }
        button("QUIT").action {
            close()
        }
    }
}
