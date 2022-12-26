package ru.nsu.engine.util

class Common {
    private val prefix: String = "file:./configuration/content/"
    fun parsePath (string: String):String {
        return prefix + string
    }
}