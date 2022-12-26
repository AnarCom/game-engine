package ru.nsu.lib.common

import com.fasterxml.jackson.annotation.JsonProperty

open class EnemyType(
    val file: String,

    val health: Int,

    val speed: Int,

    val gold: Int,

    @field:JsonProperty("enemy_attack")
    val enemyAttack: Int,
)
