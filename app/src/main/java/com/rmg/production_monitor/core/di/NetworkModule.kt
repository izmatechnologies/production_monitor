package com.rmg.production_monitor.core.di

import com.rmg.production_monitor.core.service.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.rmg.production_monitor.core.Config
import com.rmg.production_monitor.core.service.ApiService

private const val TIME_OUT = 6L
private const val CONNECTION_TIME_OUT = 3L
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    // todo separate this
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }
    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
            .protocols(listOf(Protocol.HTTP_1_1))
            .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MINUTES)
            .readTimeout(TIME_OUT, TimeUnit.MINUTES)
            .writeTimeout(TIME_OUT, TimeUnit.MINUTES)
            .addInterceptor(getLogInterceptors())
            .addInterceptor(authInterceptor)
            .followRedirects(false)
            .followSslRedirects(false)
            .build()
    }



     fun getLogInterceptors(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level =HttpLoggingInterceptor.Level.BODY


        }
    }
    @Singleton
    @Provides
    fun providesProfileViewAPI(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): ApiService {
        return retrofitBuilder.client(okHttpClient).build().create(ApiService::class.java)
    }

}