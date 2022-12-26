package ru.nsu.lib.common

class TowerData(
    val file:String,
    val upgrades:MutableList<TowerUpdate> = mutableListOf()
)
