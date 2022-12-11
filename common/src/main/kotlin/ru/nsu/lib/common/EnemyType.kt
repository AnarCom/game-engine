package ru.nsu.lib.common

import com.fasterxml.jackson.annotation.JsonProperty

open class EnemyType(
    val health: Int,

    val speed: Int,

    val file: String,

    @field:JsonProperty("enemy_attack")
    val enemyAttack: Int,
)
