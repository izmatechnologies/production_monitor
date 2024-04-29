package com.rmg.production_monitor.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.faisal.quc.base.Resource
import com.faisal.quc.models.remote.authentication.AuthenticationRequest
import com.rmg.production_monitor.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {


    val authenticate get() = authenticationRepository.authenticateLiveData
    fun doAuthenticate(requestBody: AuthenticationRequest) {

        viewModelScope.launch {


            authenticationRepository.authenticate(requestBody)
        }

    }

    fun storeAuthenticationToken(token: String?){
        authenticationRepository.storeAuthenticationToken(token)
    }


    fun getAuthToken():String?{
        return authenticationRepository.getAuthToken()
    }

    fun saveUserType(userType: String?) {
        authenticationRepository.saveUserType(userType)
    }

    fun getUserType(): String? {
        return authenticationRepository.getUserType()
    }

    fun saveUserName(userName: String?) {
        authenticationRepository.saveUserName(userName)
    }

    fun saveUnit(unit: Int?) {
        authenticationRepository.saveUnit(unit)
    }
    fun savePlant(plant: Int?) {
        authenticationRepository.savePlant(plant)
    }


}