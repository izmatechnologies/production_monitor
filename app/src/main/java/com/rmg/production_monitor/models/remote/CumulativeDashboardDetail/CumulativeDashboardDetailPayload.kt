package com.rmg.production_monitor.models.remote.CumulativeDashboardDetail


import com.google.gson.annotations.SerializedName

data class CumulativeDashboardDetailPayload(
    @SerializedName("BuyerName")
    val buyerName: String,
    @SerializedName("ColorName")
    val colorName: String,
    @SerializedName("Hour")
    val hour: String,
    @SerializedName("HourlyDetails")
    val hourlyDetails: List<HourlyDetail>,
    @SerializedName("LineId")
    val lineId: Int,
    @SerializedName("LineName")
    val lineName: String,
    @SerializedName("PoNumber")
    val poNumber: String,
    @SerializedName("StyleName")
    val styleName: String
)