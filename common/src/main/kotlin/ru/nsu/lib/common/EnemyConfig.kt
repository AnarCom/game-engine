package ru.nsu.lib.common

import com.fasterxml.jackson.annotation.JsonProperty

open class EnemyConfig(
    @field:JsonProperty("enemy_types")
    val enemyTypes: Map<String, EnemyType>,

    @field:JsonProperty("enemy_waves")
    val enemyWaves: Array<EnemyWave>
)
