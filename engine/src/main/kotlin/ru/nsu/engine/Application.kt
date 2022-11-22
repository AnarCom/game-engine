package ru.nsu.engine

import javafx.stage.Stage
import ru.nsu.engine.view.MainMenuView
import tornadofx.*

class Application:App(MainMenuView::class){
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 800.0
        stage.height = 800.0
        stage.scene.stylesheets.add("index.css")
    }
}
