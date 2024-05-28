package com.rmg.production_monitor.models.remote.cumulativeDashboardDetail


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

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
    val `actual`: Int?,
    @SerializedName("ColumnName")
    val columnName: String?,
    @SerializedName("ColumnType")
    val columnType: String?,
    @SerializedName("IsUp")
    val isUp: Boolean?,
    @SerializedName("Planned")
    val planned: Int?,
    @SerializedName("SingleValue")
    val singleValue: Int?,
    @SerializedName("Symbol")
    val symbol: String?
)

data class ColumnName(
    val name:String?
)