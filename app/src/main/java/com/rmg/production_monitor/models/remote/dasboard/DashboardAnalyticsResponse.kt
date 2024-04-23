package com.rmg.production_monitor.models.remote.dasboard
import androidx.annotation.Keep

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Keep
@Serializable
data class DashboardAnalyticsResponse(
    @SerialName("Message")
    val message: String,
    @SerialName("Payload")
    val payload: Payload,
    @SerialName("Success")
    val success: Boolean
)

@Keep
@Serializable
data class Payload(
    @SerialName("LineId")
    val lineId: Int,
    @SerialName("TotalWip")
    val totalWip: Int,
    @SerialName("WipPos")
    val wipPos: List<WipPo>,
    @SerialName("WorkingHour")
    val workingHour: Int
)

@Keep
@Serializable
data class WipPo(
    @SerialName("BuyerId")
    val buyerId: Int,
    @SerialName("BuyerName")
    val buyerName: String,
    @SerialName("ColorName")
    val colorName: String,
    @SerialName("LineCumIn")
    val lineCumIn: Int,
    @SerialName("LineCumOut")
    val lineCumOut: Int,
    @SerialName("LineWip")
    val lineWip: Int,
    @SerialName("PoId")
    val poId: Int,
    @SerialName("PoNumber")
    val poNumber: String,
    @SerialName("StyleId")
    val styleId: Int,
    @SerialName("StyleName")
    val styleName: String
)