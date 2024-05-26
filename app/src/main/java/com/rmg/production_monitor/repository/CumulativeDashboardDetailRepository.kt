package com.rmg.production_monitor.repository

import androidx.lifecycle.LiveData
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.models.remote.CumulativeDashboardDetail.CumulativeDashboardDetailModel

interface CumulativeDashboardDetailRepository {

    // Cumulative Dashboard Detail
    val cumulativeDashboardDetailLiveData: LiveData<NetworkResult<CumulativeDashboardDetailModel>>

    suspend fun getCumulativeDashboardDetail(lineId: Int)

}