package ru.nsu.engine.util

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.ButtonType

fun dialogUtil(title: String, contentText: String, alertType: AlertType, callback: () -> Unit):Alert {
    val alert = Alert(alertType)
    alert.title = title
    alert.contentText = contentText
    (alert.dialogPane.lookupButton(ButtonType.OK) as Button).setOnAction {
        callback()
    }
    return alert
}