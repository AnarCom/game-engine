package ru.nsu.engine.view

import tornadofx.*

//TODO: подгрузка данных об уровнях из конфига
//TODO: отображение isDisabled уровня по тому, пройден ли он или нет
class LevelSelectView : View("My View") {
    private val levels = listOf(1, 2, 3, 4, 5, 6, 7, 10)

    override val root = vbox {
        scrollpane {
            gridpane {
                for (i in levels) {
                    row {
                        button("Level $i"){
                            isDisable = i > 5
                        }
                    }
                }
            }
        }
        button("BACK").action {
            replaceWith<MainMenuView>()
        }
    }
}
