package ru.nsu.engine.view.subview

import javafx.scene.control.Label
import ru.nsu.engine.util.Wallet
import tornadofx.*

class TopGameSubview : View("My View") {

    var deadFlag = false

    val wallet: Wallet = Wallet(0,
        {
            moneyLabel.text = "Balance: $it $"
        },
        {
            logError("Not enough money")
        }
    )

    val hpWallet = Wallet(0,
        {
            hpLabel.text = "$it hp"
        },
        {
            logError("you are dead")
            deadFlag = true
        }
    )

    private val hpLabel: Label = label("") {

    }

    private val moneyLabel: Label = label("") {
        textFill = c("green")
    }

    private val errorLabel: Label = label("") {
        textFill = c("red")
    }


    override val root = hbox {
        add(moneyLabel)
        add(errorLabel)
        add(hpLabel)
    }

    fun logError(error: String) {
        errorLabel.text = error
    }

    fun hideErrorLabel() {
        errorLabel.text = ""
    }
}
