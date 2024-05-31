package com.rmg.production_monitor.models.remote.cumulativeDashboardDetail


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.descriptors.PrimitiveKind

@Keep
data class HourlyDetail(
    @SerializedName("ColumnValues")
    val columnValues: List<ColumnValue?>?,
    @SerializedName("Hour")
    val hour: Int?
)

@Keep
data class ColumnValue(
    @SerializedName("Actual")
    val `actual`: Double?,
    @SerializedName("ColumnName")
    val columnName: String?,
    @SerializedName("ColumnType")
    val columnType: String?,
    @SerializedName("IsUp")
    val isUp: Boolean?,
    @SerializedName("Planned")
    val planned: Double?,
    @SerializedName("SingleValue")
    val singleValue: Double?,
    @SerializedName("Symbol")
    val symbol: String?
)

data class ColumnName(
    val name:String?
)