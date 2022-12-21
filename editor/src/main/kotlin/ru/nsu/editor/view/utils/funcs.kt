package ru.nsu.editor.view.utils

import javafx.scene.control.ListView
import javafx.scene.layout.VBox
import tornadofx.*

fun getFilenameFromURI(uri: String): String {
    return uri
        .substring(
            startIndex = uri.lastIndexOf("/") + 1
        ).takeWhile {
            it != '.'
        }
}