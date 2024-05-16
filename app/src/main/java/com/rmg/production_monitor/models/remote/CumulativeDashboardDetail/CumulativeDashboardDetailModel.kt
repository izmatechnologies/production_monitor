package com.rmg.production_monitor.models.remote.CumulativeDashboardDetail


import com.google.gson.annotations.SerializedName

data class CumulativeDashboardDetailModel(
    @SerializedName("Message")
    val message: String,
    @SerializedName("Payload")
    val payload: CumulativeDashboardDetailPayload,
    @SerializedName("Success")
    val success: Boolean
)