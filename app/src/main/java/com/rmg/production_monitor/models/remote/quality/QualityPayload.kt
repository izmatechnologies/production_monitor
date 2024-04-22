package com.rmg.production_monitor.models.remote.quality

import com.google.gson.annotations.SerializedName

data class QualityPayload(
    @SerializedName("Buyer")
    val buyer: String,
    @SerializedName("Color")
    val color: String,
    @SerializedName("HeatMapIssues")
    val heatMapIssues: List<Any>,
    @SerializedName("HeatMapOperations")
    val heatMapOperations: List<Any>,
    @SerializedName("HeatMapPositions")
    val heatMapPositions: List<Any>,
    @SerializedName("MarkingImageUrl")
    val markingImageUrl: String,
    @SerializedName("OverAllDhu")
    val overAllDhu: String,
    @SerializedName("Po")
    val po: String,
    @SerializedName("RemainingDiffective")
    val remainingDiffective: Int,
    @SerializedName("StationWiseDhus")
    val stationWiseDhus: List<StationWiseDhu>,
    @SerializedName("Style")
    val style: String,
    @SerializedName("TotalReject")
    val totalReject: Int
)