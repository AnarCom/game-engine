package ru.nsu.editor

import javafx.stage.Stage
import ru.nsu.editor.view.MainMenuView
import tornadofx.*

class Application:App(MainMenuView::class){
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 800.0
        stage.height = 800.0
    }
}
