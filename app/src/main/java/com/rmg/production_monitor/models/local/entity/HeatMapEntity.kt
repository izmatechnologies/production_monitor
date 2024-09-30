package com.rmg.production_monitor.models.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.rmg.production_monitor.models.remote.quality.QualityPayload

@Entity(tableName = "HeatMap")
data class HeatMapEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @SerializedName("Payload")
    val payload: QualityPayload?
)

//data class QualityPayload(
//    @SerializedName("Buyer")
//    val buyer: String?,
//    @SerializedName("RunningDay")
//    val RunningDay: Int?,
//    @SerializedName("RununningHour")
//    val RununningHour: Int?,
//    @SerializedName("Color")
//    val color: String?,
//    @SerializedName("HeatMapIssues")
//    val heatMapIssues: List<HeatMapIssue>,
//    @SerializedName("HeatMapOperations")
//    val heatMapOperations: List<HeatMapOperation>,
//    @SerializedName("HeatMapPositions")
//    val heatMapPositions: List<HeatMapPosition>,
//    @SerializedName("ImageUrl")
//    val imageUrl: String?,
//    @SerializedName("MarkingImageUrl")
//    val markingImageUrl: String?,
//    @SerializedName("OverAllDhu")
//    val overAllDhu: String?,
//    @SerializedName("Po")
//    val po: String?,
//    @SerializedName("RemainingDiffective")
//    val remainingDiffective: Int?,
//    @SerializedName("StationWiseDhus")
//    val stationWiseDhus: List<StationWiseDhu>,
//    @SerializedName("Style")
//    val style: String?,
//    @SerializedName("TotalReject")
//    val totalReject: Int
//)
//
//data class HeatMapIssue(
//    @SerializedName("IssueName")
//    val issueName: String,
//    @SerializedName("Count")
//    val count: Int
//)
//
//data class HeatMapOperation(
//    @SerializedName("OperationName")
//    val operationName: String,
//    @SerializedName("Count")
//    val count: Int
//)
//data class HeatMapPosition(
//    @SerializedName("X")
//    val x: String,
//    @SerializedName("Y")
//    val y: String
//)
//
//data class StationWiseDhu(
//    @SerializedName("DHU")
//    val dHU: String,
//    @SerializedName("StationName")
//    val stationName: String
//)