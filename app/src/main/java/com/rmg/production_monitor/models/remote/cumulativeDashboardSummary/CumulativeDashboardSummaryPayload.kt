package com.rmg.production_monitor.models.remote.cumulativeDashboardSummary


import com.google.gson.annotations.SerializedName

data class CumulativeDashboardSummaryPayload(
    @SerializedName("Actual")
    val `actual`: String,
    @SerializedName("ActualEfficiency")
    val actualEfficiency: String,
    @SerializedName("BuyerName")
    val buyerName: String,
    @SerializedName("RunningDay")
    val RunningDay: Int,
    @SerializedName("RununningHour")
    val RununningHour: Int,
    @SerializedName("ColorName")
    val colorName: String,
    @SerializedName("DHU")
    val dHU: String,
    @SerializedName("Helpers")
    val helpers: String,
    @SerializedName("Hour")
    val hour: String,
    @SerializedName("IronMan")
    val ironMan: String,
    @SerializedName("LineId")
    val lineId: Int,
    @SerializedName("LineName")
    val lineName: String,
    @SerializedName("Operations")
    val operations: String,
    @SerializedName("PlannedEfficiency")
    val plannedEfficiency: String,
    @SerializedName("PoNumber")
    val poNumber: String,
    @SerializedName("StyleName")
    val styleName: String,
    @SerializedName("Target")
    val target: String,
    @SerializedName("Trend")
    val trend: String,
    @SerializedName("Variance")
    val variance: String,
    @SerializedName("WipTotal")
    val wipTotal: String
)