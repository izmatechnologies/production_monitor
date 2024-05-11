package com.rmg.production_monitor.core.service


import com.faisal.quc.models.remote.authentication.AuthenticationRequest
import com.rmg.production_monitor.models.remote.authentication.AuthenticateModel
import com.rmg.production_monitor.models.remote.cumulativeDashboardSummary.CumulativeDashboardSummaryModel
import com.rmg.production_monitor.models.remote.dasboard.DashboardAnalyticsResponse
import com.rmg.production_monitor.models.remote.quality.QualityModel
import retrofit2.Response
import retrofit2.http.Body


import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {


//    @Deprecated("don't this authenticate_deprecated method", replaceWith = ReplaceWith("authenticate( requestBody: AuthenticationRequest)")
//        , level = DeprecationLevel.ERROR)
    @POST("/api/AnalyticAppAuth/Authenticate")
    suspend fun authenticate(@Body requestBody: AuthenticationRequest): Response<AuthenticateModel>

    // Heat map
    @GET("api/HeeatMap/GetHeatmap")
    suspend fun getHeatmap(@Query("lineid") lineId: Int): Response<QualityModel>

    @GET("api/AnalyticsDashboard/GetWip?lineId=1")
    suspend fun getDashboardAnalytics(): Response<DashboardAnalyticsResponse>

    @GET("api/CumulativeDashboard/GetCumulativeDashboardSummary")
    suspend fun getCumulativeDashboardSummary(
        @Query("lineid") lineId: Int
    ): Response<CumulativeDashboardSummaryModel>
}