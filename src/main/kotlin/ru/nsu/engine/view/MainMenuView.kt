package ru.nsu.engine.view

import tornadofx.*

class MainMenuView : View() {

    override val root = vbox {
        button("PLAY").action {
            replaceWith<LevelSelectView>()
        }
        button("SETTINGS").action {
            replaceWith<SettingsView>()
        }
        button("QUIT").action {
            close()
        }
    }
}
