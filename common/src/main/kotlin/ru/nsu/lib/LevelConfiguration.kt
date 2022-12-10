package ru.nsu.lib

import com.fasterxml.jackson.annotation.JsonProperty
import ru.nsu.lib.common.EnemyConfig
import ru.nsu.lib.common.FieldImageInfo
import ru.nsu.lib.common.Size
import ru.nsu.lib.common.TowerData

open class LevelConfiguration(
    @field:JsonProperty("field_size")
    val fieldSize: Size,

    @field:JsonProperty("cell_size")
    val cellSize: Size,

    @field:JsonProperty("field_parts_config")
    val fieldPartsConfig: Map<String, FieldImageInfo>,

    @field:JsonProperty("field_structure")
    val fieldStructure: Array<Array<String>>,

    @field:JsonProperty("towers_config")
    val towersConfig: Map<String, TowerData>,

    @field:JsonProperty("start_money")
    val startMoney: Int,

    @field:JsonProperty("enemy_config")
    val enemyConfig: EnemyConfig,
)
