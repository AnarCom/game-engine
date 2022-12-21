package ru.nsu.editor

import javafx.stage.Stage
import ru.nsu.editor.view.EditorView
import ru.nsu.editor.view.MainMenuView
import tornadofx.*

class Application:App(EditorView::class){
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 800.0
        stage.height = 800.0
        stage.scene.stylesheets.add("index.css")
    }
}
