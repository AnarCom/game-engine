package ru.nsu.engine.view

import tornadofx.*

class ClientView() : Fragment("Client") {
    init {
        importStylesheet("/index.css")
    }

    override val root = borderpane {
        minHeight = 600.0
        minWidth = 600.0
        right {
            label("hello wrold")
        }
    }
}