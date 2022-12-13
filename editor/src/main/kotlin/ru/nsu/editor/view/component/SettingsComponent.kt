package ru.nsu.editor.view.component

import tornadofx.*

abstract class SettingsComponent<out T>(title: String): View(title) {
    abstract fun getSettings(): T

}