package ru.nsu.engine.view.subview

import javafx.scene.control.Label
import tornadofx.*
import java.util.concurrent.atomic.AtomicInteger

class TopGameSubview : View("My View") {

    private val moneyLabel: Label = label("") {
        textFill = c("green")
    }

    private val errorLabel: Label = label("") {
        textFill = c("red")
    }

    private val money: AtomicInteger = AtomicInteger(0)

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

    fun addMoney(a: Int) {
        money.addAndGet(a)
        showNewMoneyValue()
    }

    @Synchronized
    private fun showNewMoneyValue() {
        moneyLabel.text = "Balance: ${money.get()} $"
    }
}
