package com.rmg.production_monitor.repository.demo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rmg.production_monitor.core.Constants
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.service.ApiService
import com.rmg.production_monitor.models.remote.cumulativeDashboardSummary.CumulativeDashboardSummaryModel
import com.rmg.production_monitor.models.remote.dasboard.DashboardAnalyticsResponse
import com.rmg.production_monitor.models.remote.quality.QualityModel
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class QualityDemoRepository @Inject constructor(
    private val apiService: ApiService,
){
    suspend fun getHeatmap(lineId:Int): Response<QualityModel> {
        return apiService.getHeatmap(lineId)
    }
}