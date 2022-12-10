package ru.nsu.lib.common

import com.fasterxml.jackson.annotation.JsonProperty

open class EnemyWave(
    @field:JsonProperty("between_delay_ms")
    val betweenDelayMs: Int,
    val wave: Array<String>,
)