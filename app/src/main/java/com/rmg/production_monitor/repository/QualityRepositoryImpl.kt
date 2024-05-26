package com.rmg.production_monitor.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rmg.production_monitor.core.Constants
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.service.ApiService
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

        if (response.isSuccessful && response.body() != null) {
            _heatMapLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            if (response.code() == Constants.NetworkResponseStatus.UNAUTHORIZED.statusCode) _heatMapLiveData.postValue(
                NetworkResult.SessionOut("Unauthorized")
            )
            else {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _heatMapLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
            }
        } else {
            _heatMapLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    // Clear Session
}