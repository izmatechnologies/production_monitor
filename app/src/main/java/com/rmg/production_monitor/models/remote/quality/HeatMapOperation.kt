package com.rmg.production_monitor.models.remote.quality

import com.google.gson.annotations.SerializedName

data class HeatMapOperation(
    @SerializedName("OperationId")
    val operationId: Int,
    @SerializedName("OperationName")
    val operationName: String,
    @SerializedName("Count")
    val count: Int

)