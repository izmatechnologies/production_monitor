package com.rmg.production_monitor.models.remote.cumulativeDashboardDetail


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


@Keep
data class CumulativeDashboardDetailModel(
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Payload")
    val payload: CumulativeDashboardDetailPayload?,
    @SerializedName("Success")
    val success: Boolean?
)