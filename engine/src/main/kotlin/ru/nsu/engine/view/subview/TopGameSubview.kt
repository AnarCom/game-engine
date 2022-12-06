package ru.nsu.engine.view.subview

import javafx.scene.control.Label
import ru.nsu.engine.ComparableAtomicInteger
import tornadofx.*

class TopGameSubview : View("My View") {

    private val moneyLabel: Label = label("") {
        textFill = c("green")
    }

    private val errorLabel: Label = label("") {
        textFill = c("red")
    }

    private val money: ComparableAtomicInteger = ComparableAtomicInteger()

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

    fun addMoney(a: Int) {
        money.add(a)
        showNewMoneyValue()
    }

    fun writeOffMoney(a: Int, showErrorIfNot: Boolean = true): Boolean {
        if (
            money.decIfTrue(a) {
                it > a
            }
        ) {
            return true
        } else {
            if (showErrorIfNot) {
                logError("Not enough money")
            }
            return false
        }
    }

    @Synchronized
    private fun showNewMoneyValue() {
        moneyLabel.text = "Balance: ${money.get()} $"
    }
}
