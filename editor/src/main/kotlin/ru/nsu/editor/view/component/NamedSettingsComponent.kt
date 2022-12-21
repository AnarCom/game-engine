package ru.nsu.editor.view.component

import tornadofx.*

abstract class NamedSettingsComponent<out T : Any>(title: String): SettingsComponent<Pair<String, T>>(title) {
    abstract override val preset: Pair<String, T>
    val filename: String
        get() = preset.first
    val data: T
        get() = preset.second
    abstract override fun getSettings(): Pair<String, T>
}
