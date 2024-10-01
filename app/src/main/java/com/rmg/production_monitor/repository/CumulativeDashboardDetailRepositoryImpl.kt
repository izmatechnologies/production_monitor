package com.rmg.production_monitor.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.service.ApiService
import com.rmg.production_monitor.models.remote.cumulativeDashboardDetail.CumulativeDashboardDetailModel
import com.rmg.production_monitor.models.remote.cumulativeDashboardSummary.CumulativeDashboardSummaryModel
import org.json.JSONObject
import javax.inject.Inject

class CumulativeDashboardDetailRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CumulativeDashboardDetailRepository {

    // Cumulative Dashboard Detail
    private val _cumulativeDashboardDetailLiveData =
        MutableLiveData<NetworkResult<CumulativeDashboardDetailModel>>()

    override val cumulativeDashboardDetailLiveData: LiveData<NetworkResult<CumulativeDashboardDetailModel>>
        get() = _cumulativeDashboardDetailLiveData

    override suspend fun getCumulativeDashboardDetail(lineId: Int):CumulativeDashboardDetailModel {
//        _cumulativeDashboardDetailLiveData.postValue(NetworkResult.Loading())
        return apiService.getCumulativeDashboardDetail(lineId)
    }



//        try {
//            if (response.isSuccessful) {
//                if (response.body() != null && response.body() is CumulativeDashboardDetailModel) {
//                    if (response.body()!!.success == true) {
//                        _cumulativeDashboardDetailLiveData.postValue(
//                            NetworkResult.Success(
//                                response.body()!!
//                            )
//                        )
//                    } else {
//                        _cumulativeDashboardDetailLiveData.postValue(NetworkResult.Error("Not Available Data "))
//                    }
//
//                } else {
//                    _cumulativeDashboardDetailLiveData.postValue(NetworkResult.Error("Not Available Data "))
//                }
//            } else if (response.code() == 401) {
//
//                _cumulativeDashboardDetailLiveData.postValue(NetworkResult.SessionOut("Unauthorized"))
//
//            } else if (
//                response.code() == 500
//                || response.code() == 502
//                || response.code() == 503
//            ) {
//
//                _cumulativeDashboardDetailLiveData.postValue(NetworkResult.Error(" The server is currently unavailable"))
//
//            } else {
//                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
//                _cumulativeDashboardDetailLiveData.postValue(
//                    NetworkResult.Error(
//                        errorObj.getString(
//                            "message"
//                        )
//                    )
//                )
//            }
//
//        } catch (e: Exception) {
//            Log.e("error", e.message.toString())
//        }

//        if (response.isSuccessful && response.body() != null) {
//            _cumulativeDashboardDetailLiveData.postValue(NetworkResult.Success(response.body()!!))
//        } else if (response.errorBody() != null) {
//            if (response.code() == Constants.NetworkResponseStatus.UNAUTHORIZED.statusCode) {
//                _cumulativeDashboardDetailLiveData.postValue(NetworkResult.SessionOut("Unauthorized"))
//            } else {
//                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
//                _cumulativeDashboardDetailLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
//            }
//        } else {
//            _cumulativeDashboardDetailLiveData.postValue(NetworkResult.Error("Something went wrong"))
//        }
}
