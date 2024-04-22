package com.rmg.production_monitor.core.service


import com.rmg.production_monitor.core.managers.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        val token = sessionManager.fetchAuthToken()
        request.addHeader("Authorization", "Bearer $token")

        return chain.proceed(request.build())
    }
}