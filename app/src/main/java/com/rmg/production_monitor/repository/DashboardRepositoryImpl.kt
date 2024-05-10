package com.rmg.production_monitor.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rmg.production_monitor.core.Constants
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.service.ApiService
import com.rmg.production_monitor.models.remote.dasboard.DashboardAnalyticsResponse
import com.rmg.production_monitor.models.remote.quality.QualityModel
import org.json.JSONObject
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val apiService: ApiService) : DashboardRepository {

    private val _dashboardAnalyticsData = MutableLiveData<NetworkResult<DashboardAnalyticsResponse>>()
    override val dashboardAnalyticsData: LiveData<NetworkResult<DashboardAnalyticsResponse>>
        get() = _dashboardAnalyticsData

    override suspend fun getDashboardAnalytics() {
        _dashboardAnalyticsData.postValue(NetworkResult.Loading())
        val response = apiService.getDashboardAnalytics()

        if (response.isSuccessful && response.body() != null) {
            _dashboardAnalyticsData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            if (response.code() == Constants.NetworkResponseStatus.UNAUTHORIZED.statusCode) _dashboardAnalyticsData.postValue(
                NetworkResult.SessionOut("Unauthorized")
            )
            else {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _dashboardAnalyticsData.postValue(NetworkResult.Error(errorObj.getString("message")))
            }
        } else {
            _dashboardAnalyticsData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }


}