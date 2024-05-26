package com.rmg.production_monitor.models.remote.quality

import com.google.gson.annotations.SerializedName

data class QualityPayload(
    @SerializedName("Buyer")
    val buyer: String,
    @SerializedName("RunningDay")
    val RunningDay: Int,
    @SerializedName("RununningHour")
    val RununningHour: Int,
    @SerializedName("Color")
    val color: String,
    @SerializedName("HeatMapIssues")
    val heatMapIssues: List<HeatMapIssue>,
    @SerializedName("HeatMapOperations")
    val heatMapOperations: List<HeatMapOperation>,
    @SerializedName("HeatMapPositions")
    val heatMapPositions: List<HeatMapPosition>,
    @SerializedName("ImageUrl")
    val imageUrl: String,
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

data class DhuValueList(
    var name:String?,
    var value:String?
)

data class TopProductionsIssue(
    var name: String?,
    var value: Int?
)