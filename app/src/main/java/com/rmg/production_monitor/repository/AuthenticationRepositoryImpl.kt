package com.rmg.production_monitor.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.faisal.quc.models.remote.authentication.AuthenticationRequest
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.extention.log
import com.rmg.production_monitor.core.managers.preference.AppPreferenceImpl
import com.rmg.production_monitor.core.service.GeneralApiService
import com.rmg.production_monitor.models.remote.authentication.AuthenticateModel

import org.json.JSONObject
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val apiService: GeneralApiService,
    private val appPreference: AppPreferenceImpl,
) : AuthenticationRepository {

    private val TAG: String = AuthenticationRepository::class.java.name
    private val _authenticateLiveData = MutableLiveData<NetworkResult<AuthenticateModel>>()

    override val authenticateLiveData: LiveData<NetworkResult<AuthenticateModel>>
        get() = _authenticateLiveData

    override suspend fun authenticate(requestBody: AuthenticationRequest) {
        try {
            _authenticateLiveData.postValue(NetworkResult.Loading())
            val response = apiService.authenticate(requestBody)
//            if (response.code() == 200) {
//              //  log("success")
//            } else {
//             //   log("error")
//            }

            if (response.isSuccessful && response.body() != null) {
                if (response.body() is AuthenticateModel) {
                    _authenticateLiveData.postValue(NetworkResult.Success(response.body()!!))
                } else {
                    _authenticateLiveData.postValue(NetworkResult.Error("Something went wrong"))
                }
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _authenticateLiveData.postValue(NetworkResult.Error(errorObj.getString("massage")))
            } else {
                _authenticateLiveData.postValue(NetworkResult.Error("Something went wrong"))
            }

        } catch (e: Exception) {
            _authenticateLiveData.postValue(NetworkResult.Error(e.message.toString()))
        }
    }

    override fun storeAuthenticationToken(token: String?) {
        appPreference.userToken = token
    }

    override fun getAuthToken(): String? {
        return appPreference.userToken
    }


    override fun saveUserName(userName: String?) {
        appPreference.userName = userName
    }

    override fun saveUnit(unit: Int?) {
        appPreference.unitId = unit
    }

    override fun savePlant(plant: Int?) {
        appPreference.plantId = plant
    }

    override fun saveUserLine(userLine: Int?) {
        appPreference.lineId = userLine
    }
}

