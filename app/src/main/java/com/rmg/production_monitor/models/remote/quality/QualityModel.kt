package com.rmg.production_monitor.models.remote.quality

import com.google.gson.annotations.SerializedName

data class QualityModel(
    @SerializedName("Message")
    val message: String,
    @SerializedName("Payload")
    val payload: QualityPayload,
    @SerializedName("Success")
    val success: Boolean
)