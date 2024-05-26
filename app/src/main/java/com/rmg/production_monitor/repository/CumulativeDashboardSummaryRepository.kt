package com.rmg.production_monitor.repository

import androidx.lifecycle.LiveData
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.models.remote.cumulativeDashboardSummary.CumulativeDashboardSummaryModel

interface CumulativeDashboardSummaryRepository {

    // Cumulative Dashboard Summary
    val cumulativeDashboardSummaryLiveData: LiveData<NetworkResult<CumulativeDashboardSummaryModel>>

    suspend fun getCumulativeDashboardSummary(lineId: Int)

}