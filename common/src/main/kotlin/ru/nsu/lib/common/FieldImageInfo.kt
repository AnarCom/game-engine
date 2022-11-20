package ru.nsu.lib.common

import com.fasterxml.jackson.annotation.JsonProperty

class FieldImageInfo(
   val file:String,

   @field:JsonProperty("is_build_available")
   val isBuildAvailable:Boolean,
)
