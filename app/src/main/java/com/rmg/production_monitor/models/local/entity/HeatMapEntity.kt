package com.rmg.production_monitor.models.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.rmg.production_monitor.models.remote.quality.QualityPayload

@Entity(tableName = "HeatMap")
data class HeatMapEntity(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    @SerializedName("Payload")
    val payload: QualityPayload?
)