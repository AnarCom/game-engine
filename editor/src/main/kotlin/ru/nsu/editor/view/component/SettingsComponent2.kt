package ru.nsu.editor.view.component

import tornadofx.*

abstract class SettingsComponent2<T>(title: String): View(title) {
    abstract fun getSettings(): T

}