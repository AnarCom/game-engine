package ru.nsu.engine.view.util

import javafx.scene.Cursor

enum class ActionOnClick(
    val cancelAvailable:Boolean = true,
    val cursor: Cursor = Cursor.DEFAULT
    ) {
    BUILD(cursor = Cursor.HAND),
    DELETE(cursor = Cursor.CROSSHAIR),
    NONE(cancelAvailable = false)
}