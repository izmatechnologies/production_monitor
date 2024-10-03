package com.rmg.production_monitor.core.di

import com.rmg.production_monitor.core.Config
import com.rmg.production_monitor.core.service.AuthInterceptor
import com.rmg.production_monitor.core.service.DashboardApiService
import com.rmg.production_monitor.core.service.PcbApiService
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
import javax.inject.Qualifier
import javax.inject.Singleton

private const val TIME_OUT = 60L
private const val CONNECTION_TIME_OUT = 30L
@Module
@InstallIn(SingletonComponent::class)
class DashboardNetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DashboardClient

    // primary OkHttpClient
    @Singleton
    @Provides
    @DashboardClient
    fun provideDefaultHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
            .protocols(listOf(Protocol.HTTP_1_1))
            .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(getLogInterceptors())
            .addInterceptor(authInterceptor)
            .followRedirects(false)
            .followSslRedirects(false)
            .build()
    }


    // Logging Interceptor
    fun getLogInterceptors(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // primary Retrofit
    @Provides
    @Singleton
    @RetrofitDashboard
    fun provideRetrofitInstance(@DashboardClient defaultHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(defaultHttpClient)
            .build()


    // Api Services
    @Provides
    @Singleton
    fun provideRestApis(@RetrofitDashboard retrofit: Retrofit): DashboardApiService =
        retrofit.create(DashboardApiService::class.java)



    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RetrofitDashboard


}
