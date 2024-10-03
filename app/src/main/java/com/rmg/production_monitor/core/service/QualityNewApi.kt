package com.rmg.production_monitor.core.service

import com.rmg.production_monitor.models.remote.quality.QualityModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QualityNewApi {
    // Heat map
    @GET("api/HeeatMap/GetHeatmap")
    suspend fun getHeatmap(@Query("lineid") lineId: Int): QualityModel
}