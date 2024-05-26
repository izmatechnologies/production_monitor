package com.rmg.production_monitor.repository

import androidx.lifecycle.LiveData



import com.faisal.quc.models.remote.authentication.AuthenticationRequest
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.models.remote.authentication.AuthenticateModel


interface AuthenticationRepository {


    val authenticateLiveData: LiveData<NetworkResult<AuthenticateModel>>

    suspend fun authenticate(requestBody: AuthenticationRequest)

    fun storeAuthenticationToken(token: String?)
    fun getAuthToken(): String?
    fun saveUserName(userName: String?)

    fun saveUnit(unit:Int?)
    fun savePlant(plant:Int?)
    fun saveUserLine(userLine:Int?)



}