package com.rmg.production_monitor.repository

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
import javax.inject.Inject

class QualityRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : QualityRepository {
    // Heat map
    private val _heatMapLiveData = MutableLiveData<NetworkResult<QualityModel>>()
    override val heatMapLiveData: LiveData<NetworkResult<QualityModel>>
        get() = _heatMapLiveData

    override suspend fun getHeatmap(lineId: Int) {
        _heatMapLiveData.postValue(NetworkResult.Loading())
        val response = apiService.getHeatmap(lineId)

        try {
            if (response.isSuccessful) {
                if (response.body() != null && response.body() is QualityModel) {
                    if (response.body()!!.success) {
                        _heatMapLiveData.postValue(
                            NetworkResult.Success(
                                response.body()!!
                            )
                        )
                    } else {
                        _heatMapLiveData.postValue(NetworkResult.Error("Not Available Data "))
                    }

                } else {
                    _heatMapLiveData.postValue(NetworkResult.Error("not available Data "))
                }
            } else if (response.code() == 401) {

                _heatMapLiveData.postValue(NetworkResult.SessionOut("Unauthorized"))

            } else if (
                response.code() == 500
                || response.code() == 502
                || response.code() == 503
            ) {

                _heatMapLiveData.postValue(NetworkResult.Error(" The server is currently unavailable"))

            } else {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _heatMapLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
            }

        } catch (e: Exception) {
            Log.e("error", e.message.toString())
        }
    }
//        if (response.isSuccessful && response.body() != null) {
//            _heatMapLiveData.postValue(NetworkResult.Success(response.body()!!))
//        } else if (response.errorBody() != null) {
//            if (response.code() == Constants.NetworkResponseStatus.UNAUTHORIZED.statusCode) _heatMapLiveData.postValue(
//                NetworkResult.SessionOut("Unauthorized")
//            )
//            else {
//                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
//                _heatMapLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
//            }
//        } else {
//            _heatMapLiveData.postValue(NetworkResult.Error("Something went wrong"))
//        }
//    }

    // Clear Session
}