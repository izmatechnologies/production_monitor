package com.rmg.production_monitor.core.service

import com.rmg.production_monitor.models.remote.cumulativeDashboardSummary.CumulativeDashboardSummaryModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DashboardApiService {
    @GET("api/CumulativeDashboard/GetCumulativeDashboardSummary")
    suspend fun getCumulativeDashboardSummary(
        @Query("lineid") lineId: Int
    ): CumulativeDashboardSummaryModel
}