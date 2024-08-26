package com.rmg.production_monitor.models.remote.cumulativeDashboardSummary


import com.google.gson.annotations.SerializedName

data class CumulativeDashboardSummaryPayload(
    @SerializedName("Actual")
    val `actual`: String?,
    @SerializedName("ActualEfficiency")
    val actualEfficiency: Double?,
    @SerializedName("BuyerName")
    val buyerName: String?,
    @SerializedName("RunDay")
    val runDay: String?,
    @SerializedName("RunningHour")
    val runningHour: String?,
    @SerializedName("ColorName")
    val colorName: String?,
    @SerializedName("DHU")
    val dHU: Double?,
    @SerializedName("Helpers")
    val helpers: String?,
    @SerializedName("Hour")
    val hour: String?,
    @SerializedName("IronMen")
    val ironMen: String?,
    @SerializedName("DayTarget")
    val dayTarget: String?,
    @SerializedName("LineId")
    val lineId: Int?,
    @SerializedName("LineName")
    val lineName: String?,
    @SerializedName("Operations")
    val operations: String?,
    @SerializedName("PlannedEfficiency")
    val plannedEfficiency: Double?,
    @SerializedName("PoNumber")
    val poNumber: String?,
    @SerializedName("StyleName")
    val styleName: String?,
    @SerializedName("Target")
    val target: String?,
    @SerializedName("Trend")
    val trend: String?,
    @SerializedName("Variance")
    val variance: String?,
    @SerializedName("WipTotal")
    val wipTotal: String?,
    @SerializedName("Operators")
    val operators: String?
)