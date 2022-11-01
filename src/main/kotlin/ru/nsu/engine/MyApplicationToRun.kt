package ru.nsu.engine

import tornadofx.*

class MyApplicationToRun {
    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            launch<Application>(args)
        }
    }
}