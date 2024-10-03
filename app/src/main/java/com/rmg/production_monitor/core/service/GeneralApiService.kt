package com.rmg.production_monitor.core.service


import com.faisal.quc.models.remote.authentication.AuthenticationRequest
import com.rmg.production_monitor.models.remote.authentication.AuthenticateModel
import retrofit2.Response
import retrofit2.http.Body


import retrofit2.http.POST


interface GeneralApiService {


//    @Deprecated("don't this authenticate_deprecated method", replaceWith = ReplaceWith("authenticate( requestBody: AuthenticationRequest)")
//        , level = DeprecationLevel.ERROR)
    @POST("api/AnalyticAppAuth/Authenticate")
    suspend fun authenticate(@Body requestBody: AuthenticationRequest): Response<AuthenticateModel>




}