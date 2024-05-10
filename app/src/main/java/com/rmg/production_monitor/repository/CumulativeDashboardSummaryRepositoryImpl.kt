package com.rmg.production_monitor.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rmg.production_monitor.core.Constants
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.service.ApiService
import com.rmg.production_monitor.models.remote.cumulativeDashboardSummary.CumulativeDashboardSummaryModel
import org.json.JSONObject
import javax.inject.Inject

class CumulativeDashboardSummaryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CumulativeDashboardSummaryRepository {

    // Cumulative Dashboard Summary
    private val _cumulativeDashboardSummaryLiveData = MutableLiveData<NetworkResult<CumulativeDashboardSummaryModel>>()

    override val cumulativeDashboardSummaryLiveData: LiveData<NetworkResult<CumulativeDashboardSummaryModel>>
        get() = _cumulativeDashboardSummaryLiveData

    override suspend fun getCumulativeDashboardSummary(lineId: Int) {
        _cumulativeDashboardSummaryLiveData.postValue(NetworkResult.Loading())
        val response = apiService.getCumulativeDashboardSummary(lineId)

        if (response.isSuccessful && response.body() != null) {
            _cumulativeDashboardSummaryLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            if (response.code() == Constants.NetworkResponseStatus.UNAUTHORIZED.statusCode) {
                _cumulativeDashboardSummaryLiveData.postValue(NetworkResult.SessionOut("Unauthorized"))
            } else {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _cumulativeDashboardSummaryLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
            }
        } else {
            _cumulativeDashboardSummaryLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}