package ru.nsu.engine.util

class ComparableAtomicInteger(
    initialValue: Int = 0
) {
    private var value = initialValue

    fun add(v: Int = 1) = synchronized(this) {
        value += v
    }

    fun get(): Int = synchronized(this) {
        value
    }

    fun dec(v: Int = 1) = synchronized(this) {
        value -= v
    }

    fun decIfTrue(v: Int = 1, predicate: (v: Int) -> Boolean): Boolean {
        synchronized(this) {
            if (predicate(value)) {
                value -= v
                return true
            }
        }
        return false
    }

}