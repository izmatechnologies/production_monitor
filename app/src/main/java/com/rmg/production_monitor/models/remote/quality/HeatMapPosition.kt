package com.rmg.production_monitor.models.remote.quality

import com.google.gson.annotations.SerializedName

data class HeatMapPosition(
    @SerializedName("X")
    val x: Double,
    @SerializedName("Y")
    val y: Double
)