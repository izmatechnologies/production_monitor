package com.rmg.production_monitor.models.remote.CumulativeDashboardDetail


import com.google.gson.annotations.SerializedName

data class HourlyDetail(
    @SerializedName("CumulativePcsActualPlan")
    val cumulativePcsActualPlan: String,
    @SerializedName("Hour")
    val hour: Int,
    @SerializedName("HourlyPcsActualPlan")
    val hourlyPcsActualPlan: String,
    @SerializedName("VariancePcsHourlyCum")
    val variancePcsHourlyCum: String
)