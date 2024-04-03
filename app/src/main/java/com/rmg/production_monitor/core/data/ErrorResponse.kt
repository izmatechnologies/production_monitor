package com.faisal.quc.core.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
class ErrorResponse(
    var success: Boolean = false,
    var message: String = "",
    var code: Int = 0,
    val error: JsonObject? = null
)