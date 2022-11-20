package ru.nsu.lib

import com.fasterxml.jackson.annotation.JsonProperty
import ru.nsu.lib.common.FieldImageInfo
import ru.nsu.lib.common.Size

open class LevelConfiguration(
    @field:JsonProperty("field_size")
    val fieldSize: Size,

    @field:JsonProperty("cell_size")
    val sellSize: Size,

    @field:JsonProperty("field_parts_config")
    val fieldPartsConfig: Map<String, FieldImageInfo>,

    @field:JsonProperty("field_structure")
    val fieldStructure: Array<Array<String>>,
)
