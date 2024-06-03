package com.rmg.production_monitor.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.service.ApiService
import com.rmg.production_monitor.models.remote.cumulativeDashboardSummary.CumulativeDashboardSummaryModel
import org.json.JSONObject
import javax.inject.Inject

class CumulativeDashboardSummaryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CumulativeDashboardSummaryRepository {

    // Cumulative Dashboard Summary
    private val _cumulativeDashboardSummaryLiveData =
        MutableLiveData<NetworkResult<CumulativeDashboardSummaryModel>>()

    override val cumulativeDashboardSummaryLiveData: LiveData<NetworkResult<CumulativeDashboardSummaryModel>>
        get() = _cumulativeDashboardSummaryLiveData

    override suspend fun getCumulativeDashboardSummary(lineId: Int) {
        _cumulativeDashboardSummaryLiveData.postValue(NetworkResult.Loading())
        val response = apiService.getCumulativeDashboardSummary(lineId)

        try {
            if (response.isSuccessful) {
                if (response.body() != null && response.body() is CumulativeDashboardSummaryModel) {
                    if (response.body()!!.success) {
                        _cumulativeDashboardSummaryLiveData.postValue(
                            NetworkResult.Success(
                                response.body()!!
                            )
                        )
                    } else {
                        _cumulativeDashboardSummaryLiveData.postValue(NetworkResult.Error("Not Available Data "))
                    }

                } else {
                    _cumulativeDashboardSummaryLiveData.postValue(NetworkResult.Error("not available Data "))
                }
            } else if (response.code() == 401) {

                _cumulativeDashboardSummaryLiveData.postValue(NetworkResult.SessionOut("Unauthorized"))

            } else if (
                response.code() == 500
                || response.code() == 502
                || response.code() == 503
            ) {

                _cumulativeDashboardSummaryLiveData.postValue(NetworkResult.Error(" The server is currently unavailable"))

            } else {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _cumulativeDashboardSummaryLiveData.postValue(
                    NetworkResult.Error(
                        errorObj.getString(
                            "message"
                        )
                    )
                )
            }

        } catch (e: Exception) {
            Log.e("error", e.message.toString())
        }
    }
//        if (response.isSuccessful && response.body() != null) {
//            _cumulativeDashboardSummaryLiveData.postValue(NetworkResult.Success(response.body()!!))
//        } else if (response.errorBody() != null) {
//            if (response.code() == Constants.NetworkResponseStatus.UNAUTHORIZED.statusCode) {
//                _cumulativeDashboardSummaryLiveData.postValue(NetworkResult.SessionOut("Unauthorized"))
//            } else {
//                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
//                _cumulativeDashboardSummaryLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
//            }
//        } else {
//            _cumulativeDashboardSummaryLiveData.postValue(NetworkResult.Error("Something went wrong"))
//        }

}