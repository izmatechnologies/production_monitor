package com.rmg.production_monitor.models.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.rmg.production_monitor.models.remote.cumulativeDashboardSummary.CumulativeDashboardSummaryPayload

@Entity(tableName = "cumulative_dashboard")
data class CumulativeDashBoardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("Payload")
    val payload: CumulativeDashboardSummaryPayload?,
)