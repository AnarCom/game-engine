package ru.nsu.editor.view.component

import ru.nsu.lib.common.TowerData
import tornadofx.*

abstract class SettingsComponent<out T>(title: String): View(title){
    abstract val preset: T
    abstract fun getSettings(): T
}
