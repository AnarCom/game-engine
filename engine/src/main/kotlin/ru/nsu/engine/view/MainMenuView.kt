package ru.nsu.engine.view

import tornadofx.*

class MainMenuView : View() {

    override val root = vbox {
        vboxConstraints {
            marginLeftRight(50.0)
        }
        vbox {
            button("PLAY").action {
                replaceWith<LevelSelectView>()
                vboxConstraints {
                    marginTop = 60.0
                }
            }
        }

            button("SETTINGS").action {
                replaceWith<SettingsView>()
                vboxConstraints {
                    marginTop = 60.0
                    marginLeftRight(50.0)
                }
            }

            button("QUIT").action {
                close()
            }
    }
}

