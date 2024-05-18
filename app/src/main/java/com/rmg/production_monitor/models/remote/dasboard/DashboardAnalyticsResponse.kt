package com.rmg.production_monitor.models.remote.dasboard
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Keep
data class DashboardAnalyticsResponse(
    @SerializedName("Message")
    val message: String,
    @SerializedName("Payload")
    val payload: Payload,
    @SerializedName("Success")
    val success: Boolean
)

@Keep
data class Payload(
    @SerializedName("LineId")
    val lineId: Int,
    @SerializedName("TotalWip")
    val totalWip: Int,
    @SerializedName("WipPos")
    val wipPos: List<WipPo>,
    @SerializedName("WorkingHour")
    val workingHour: Int
)

@Keep
data class WipPo(
    @SerializedName("BuyerId")
    val buyerId: Int,
    @SerializedName("BuyerName")
    val buyerName: String,
    @SerializedName("ColorName")
    val colorName: String,
    @SerializedName("LineCumIn")
    val lineCumIn: Int,
    @SerializedName("LineCumOut")
    val lineCumOut: Int,
    @SerializedName("LineWip")
    val lineWip: Int,
    @SerializedName("PoId")
    val poId: Int,
    @SerializedName("PoNumber")
    val poNumber: String,
    @SerializedName("StyleId")
    val styleId: Int,
    @SerializedName("StyleName")
    val styleName: String,
    @SerializedName("TotalReject")
    val totalReject: Int
)