package ru.nsu.editor.view.component

import tornadofx.*

abstract class NamedSettingsComponent<out T>(title: String): View(title) {
    abstract fun getSettings(): Pair<String, T>
}
