package ru.nsu.lib.common

import com.fasterxml.jackson.annotation.JsonProperty

open class TowerUpdate(
    val radius: Int,

    @field:JsonProperty("max_enemy_count")
    val maxEnemyCount: Int,

    val cost: Int,

    @field:JsonProperty("shooting_speed")
    val shootingSpeed: Int,

    @field:JsonProperty("enemy_damage")
    val enemyDamage: Int,
)
