package ru.nsu.engine.view

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ru.nsu.engine.view.state.GameState
import ru.nsu.lib.LevelConfiguration
import tornadofx.*
import java.io.File

class GameView : View("My View") {
    private val levelConfiguration: LevelConfiguration

    init {
        print(GameState.levelData!!.config)
        val mapper = jacksonObjectMapper()
        levelConfiguration = mapper.readValue(
            File(
                "./configuration/levels_configs/${GameState.levelData!!.config}"
            )
        )
    }

    override val root = vbox {
    }
}
