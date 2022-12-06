package ru.nsu.engine.view.subview

import javafx.scene.control.Label
import ru.nsu.engine.Wallet
import tornadofx.*

class TopGameSubview : View("My View") {

    val wallet: Wallet = Wallet(0,
        {
            moneyLabel.text = "Balance: $it $"
        },
        {
            logError("Not enough money")
        }
    )

    private val moneyLabel: Label = label("") {
        textFill = c("green")
    }

    private val errorLabel: Label = label("") {
        textFill = c("red")
    }


    override val root = hbox {
        add(moneyLabel)
        add(errorLabel)
    }

    fun logError(error: String) {
        errorLabel.text = error
    }

    fun hideErrorLabel() {
        errorLabel.text = ""
    }
}
