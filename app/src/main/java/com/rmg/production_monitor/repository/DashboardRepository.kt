package com.rmg.production_monitor.repository

import androidx.lifecycle.LiveData
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.models.remote.dasboard.DashboardAnalyticsResponse
import com.rmg.production_monitor.models.remote.quality.QualityModel

interface DashboardRepository {

    val dashboardAnalyticsData: LiveData<NetworkResult<DashboardAnalyticsResponse>>

    suspend fun getDashboardAnalytics(lineId:Int)



}