package ru.nsu.engine.view

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ru.nsu.engine.view.state.GameState
import ru.nsu.lib.LevelData
import tornadofx.*
import java.io.File

class LevelSelectView : View("My View") {
    private val levels: List<LevelData>

    init {
        val mapper = jacksonObjectMapper()
        levels = mapper.readValue(
            File("./configuration/levels.json")
        )
    }


    override val root = vbox {
        scrollpane {
            gridpane {
                for (i in levels) {
                    row {
                        button(i.name) {
                            action {
                                GameState.levelData = i
//                                replaceWith<GameView>()
                                val modal = find<GameView>()
                                modal.openModal()
                            }
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
