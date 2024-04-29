package com.rmg.production_monitor.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.faisal.quc.models.remote.authentication.AuthenticationRequest
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.extention.log
import com.rmg.production_monitor.core.managers.session.SessionManager
import com.rmg.production_monitor.core.service.ApiService
import com.rmg.production_monitor.models.remote.authentication.AuthenticateModel

import org.json.JSONObject
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager,
) : AuthenticationRepository {

    private val TAG: String = AuthenticationRepository::class.java.name
    private val _authenticateLiveData = MutableLiveData<NetworkResult<AuthenticateModel>>()

    override val authenticateLiveData: LiveData<NetworkResult<AuthenticateModel>>
        get() = _authenticateLiveData

    override suspend fun authenticate(requestBody: AuthenticationRequest) {

        // todo pin server snapet
        //"http://118.67.215.180/"
//        withContext(Dispatchers.IO){
//            Thread {
//                val pingManager = PingManagerImp()
//                pingManager.doPing(URL("https://www.google.com:443/"))
//
//            }.start()
//        }
        _authenticateLiveData.postValue(NetworkResult.Loading())
        val response = apiService.authenticate(requestBody)

        if (response.isSuccessful && response.body() != null) {
            _authenticateLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _authenticateLiveData.postValue(NetworkResult.Error(errorObj.getString("massage")))
        } else {
            _authenticateLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
//        val data = safeApiCall(Dispatchers.IO) {
//            apiService.authenticate(requestBody)
//        }

    }

    override fun storeAuthenticationToken(token: String?) {
        " store AuthenticationToken  : $token".log(TAG)
        sessionManager.saveAuthToken(token)
    }

    override fun getAuthToken(): String? {
        val token = sessionManager.fetchAuthToken()
        " get AuthenticationToken  : $token".log(TAG)
        return token
    }

    override fun saveUserType(userType: String?) {
        sessionManager.saveUserType(userType)
    }

    override fun getUserType(): String? {
        return sessionManager.getUserType()
    }

    override fun saveUserName(userName: String?) {
        sessionManager.saveUserName(userName)
    }

    override fun saveUnit(unit: Int?) {
       sessionManager.saveUnitId(unit)
    }

    override fun savePlant(plant: Int?) {
        sessionManager.savePlantId(plant)
    }
}

