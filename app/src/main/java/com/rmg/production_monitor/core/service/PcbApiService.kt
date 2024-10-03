package com.rmg.production_monitor.core.service

import com.faisal.quc.models.remote.authentication.AuthenticationRequest
import com.rmg.production_monitor.models.remote.authentication.AuthenticateModel
import com.rmg.production_monitor.models.remote.cumulativeDashboardDetail.CumulativeDashboardDetailModel
import com.rmg.production_monitor.models.remote.cumulativeDashboardSummary.CumulativeDashboardSummaryModel
import com.rmg.production_monitor.models.remote.dasboard.DashboardAnalyticsResponse
import com.rmg.production_monitor.models.remote.quality.QualityModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PcbApiService {

// PCB
@GET("api/CumulativeDashboard/GetCumulativeDashboardDetail")
suspend fun getCumulativeDashboardDetail(
    @Query("lineid") lineId: Int
):CumulativeDashboardDetailModel


}