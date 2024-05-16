package com.rmg.production_monitor.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rmg.production_monitor.core.Constants
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.service.ApiService
import com.rmg.production_monitor.models.remote.CumulativeDashboardDetail.CumulativeDashboardDetailModel
import org.json.JSONObject
import javax.inject.Inject

class CumulativeDashboardDetailRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CumulativeDashboardDetailRepository {

    // Cumulative Dashboard Detail
    private val _cumulativeDashboardDetailLiveData = MutableLiveData<NetworkResult<CumulativeDashboardDetailModel>>()

    override val cumulativeDashboardDetailLiveData: LiveData<NetworkResult<CumulativeDashboardDetailModel>>
        get() = _cumulativeDashboardDetailLiveData

    override suspend fun getCumulativeDashboardDetail(lineId: Int) {
        _cumulativeDashboardDetailLiveData.postValue(NetworkResult.Loading())
        val response = apiService.getCumulativeDashboardDetail(lineId)

        if (response.isSuccessful && response.body() != null) {
            _cumulativeDashboardDetailLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            if (response.code() == Constants.NetworkResponseStatus.UNAUTHORIZED.statusCode) {
                _cumulativeDashboardDetailLiveData.postValue(NetworkResult.SessionOut("Unauthorized"))
            } else {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _cumulativeDashboardDetailLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
            }
        } else {
            _cumulativeDashboardDetailLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}