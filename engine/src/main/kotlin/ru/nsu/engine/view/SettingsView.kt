package ru.nsu.engine.view

import tornadofx.*
import kotlin.math.roundToInt

// TODO: save in file data about sound settings (?)
// TODO: add start value of label and slider (from file if it is exists or 100 as default)
class SettingsView : View("My View") {
    override val root = vbox {

        val valueLabel = label("value")

        slider(0, 100, 2) {
            this.valueProperty().addListener { _, _, new ->
                val v = new.toDouble().roundToInt()
                this.value = v.toDouble()
                valueLabel.text = v.toString()
            }
        }

        button("BACK") {
            action {
                replaceWith<MainMenuView>()
            }
        }
    }
}
