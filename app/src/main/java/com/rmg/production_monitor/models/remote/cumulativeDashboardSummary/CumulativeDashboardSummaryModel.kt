package com.rmg.production_monitor.models.remote.cumulativeDashboardSummary


import com.google.gson.annotations.SerializedName

data class CumulativeDashboardSummaryModel(
    @SerializedName("Message")
    val message: String,
    @SerializedName("Payload")
    val payload: CumulativeDashboardSummaryPayload,
    @SerializedName("Success")
    val success: Boolean
)