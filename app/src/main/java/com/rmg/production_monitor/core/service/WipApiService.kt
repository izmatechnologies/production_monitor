package com.rmg.production_monitor.core.service

import com.rmg.production_monitor.models.remote.dasboard.DashboardAnalyticsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WipApiService {

    @GET("api/AnalyticsDashboard/GetWip")
    suspend fun getDashboardAnalytics(@Query("lineId") lineId: Int): DashboardAnalyticsResponse


}