package ru.nsu.engine.view.subview

import javafx.scene.control.Button
import ru.nsu.engine.view.GameView
import ru.nsu.engine.view.util.ActionOnClick
import ru.nsu.lib.common.TowerData
import tornadofx.*

class BuildTowerSubview(
    private val towersConfig: Map<String, TowerData>,
    private val gameView:GameView,
) : View() {
    var selectedTowerType: String = ""
        private set

    val canselButton: Button = button("cancel") {
        isDisable = true
        action {
            gameView.actionOnClick = ActionOnClick.NONE
        }
    }


    override val root = vbox {
        for (i in towersConfig.keys) {
            button("Построить $i") {
                action {
                    selectedTowerType = i
                    gameView.actionOnClick = ActionOnClick.BUILD
                }
            }
        }
        button("delete tower") {
            action {
                gameView.actionOnClick = ActionOnClick.DELETE
            }
        }
        add(canselButton)
    }
}
