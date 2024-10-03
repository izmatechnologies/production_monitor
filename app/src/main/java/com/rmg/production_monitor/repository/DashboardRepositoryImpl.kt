package com.rmg.production_monitor.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.service.WipApiService
import com.rmg.production_monitor.models.remote.dasboard.DashboardAnalyticsResponse
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val apiService: WipApiService
) : DashboardRepository {

    private val _dashboardAnalyticsData =
        MutableLiveData<NetworkResult<DashboardAnalyticsResponse>>()
    override val dashboardAnalyticsData: LiveData<NetworkResult<DashboardAnalyticsResponse>>
        get() = _dashboardAnalyticsData

    override suspend fun getDashboardAnalytics(lineId: Int):DashboardAnalyticsResponse {
//        _dashboardAnalyticsData.postValue(NetworkResult.Loading())
        return apiService.getDashboardAnalytics(lineId)
//        try {
//            if (response.isSuccessful) {
//                if (response.body() != null && response.body() is DashboardAnalyticsResponse) {
//                    if (response.body()!!.success) {
//                        _dashboardAnalyticsData.postValue(
//                            NetworkResult.Success(
//                                response.body()!!
//                            )
//                        )
//                    } else {
//                        _dashboardAnalyticsData.postValue(NetworkResult.Error("Not Available Data "))
//                    }
//
//                } else {
//                    _dashboardAnalyticsData.postValue(NetworkResult.Error("not available Data "))
//                }
//            } else if (response.code() == 401) {
//
//                _dashboardAnalyticsData.postValue(NetworkResult.SessionOut("Unauthorized"))
//
//            } else if (
//                response.code() == 500
//                || response.code() == 502
//                || response.code() == 503
//            ) {
//
//                _dashboardAnalyticsData.postValue(NetworkResult.Error(" The server is currently unavailable"))
//
//            } else {
//                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
//                _dashboardAnalyticsData.postValue(NetworkResult.Error(errorObj.getString("message")))
//            }
//
//        } catch (e: Exception) {
//            Log.e("error", e.message.toString())
//        }
//        if (response.isSuccessful && response.body() != null) {
//            _dashboardAnalyticsData.postValue(NetworkResult.Success(response.body()!!))
//        } else if (response.errorBody() != null) {
//            if (response.code() == Constants.NetworkResponseStatus.UNAUTHORIZED.statusCode) _dashboardAnalyticsData.postValue(
//                NetworkResult.SessionOut("Unauthorized")
//            )
//            else {
//                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
//                _dashboardAnalyticsData.postValue(NetworkResult.Error(errorObj.getString("message")))
//            }
//        } else {
//            _dashboardAnalyticsData.postValue(NetworkResult.Error("Something went wrong"))
//        }
    }


}