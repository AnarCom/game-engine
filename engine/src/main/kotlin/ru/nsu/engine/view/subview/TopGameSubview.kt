package ru.nsu.engine.view.subview

import javafx.scene.control.Label
import tornadofx.*

class TopGameSubview : View("My View") {
    private val errorLabel: Label = label("") {
        textFill = c("red")
    }

    override val root = hbox {
        add(errorLabel)
        label("asdasdasd")
    }

    fun logError(error: String) {
        errorLabel.text = error
    }

    fun hideErrorLabel() {
        errorLabel.text = ""
    }
}
