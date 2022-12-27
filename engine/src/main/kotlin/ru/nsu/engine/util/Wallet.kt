package ru.nsu.engine.util

class Wallet(
    initialValue: Int,
    private val changeHandler: (Int) -> Unit,
    private val actionOfUnavailableWriteOff: (Int) -> Unit
) {
    private var value: ComparableAtomicInteger = ComparableAtomicInteger(initialValue)

    fun addMoney(v: Int) = synchronized(this) {
        value.add(v)
        changeHandler(value.get())
    }

    fun get() = synchronized(this) {
        value.get()
    }

    fun writeOffMoney(v: Int) = synchronized(this) {
        value.dec(v)
        changeHandler(value.get())
    }

    fun writeOffMoneyIfCan(v: Int):Boolean = synchronized(this) {
        val res = value.decIfTrue(v) { it >= v }
        changeHandler(value.get())
        if(!res){
            actionOfUnavailableWriteOff(value.get())
        }
        return res
    }

}