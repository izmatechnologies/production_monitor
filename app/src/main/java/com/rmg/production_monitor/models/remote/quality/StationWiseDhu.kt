package com.rmg.production_monitor.models.remote.quality

import com.google.gson.annotations.SerializedName

data class StationWiseDhu(
    @SerializedName("DHU")
    val dHU: String,
    @SerializedName("StationId")
    val stationId: Int,
    @SerializedName("StationName")
    val stationName: String
)