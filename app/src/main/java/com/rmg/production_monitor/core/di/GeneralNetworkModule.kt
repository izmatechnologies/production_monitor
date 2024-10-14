package com.rmg.production_monitor.core.di

import com.rmg.production_monitor.core.Config
import com.rmg.production_monitor.core.managers.preference.AppPreferenceImpl
import com.rmg.production_monitor.core.service.AuthInterceptor
import com.rmg.production_monitor.core.service.GeneralApiService
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
class GeneralNetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DefaultClient



    // primary OkHttpClient
    @Singleton
    @Provides
    @DefaultClient
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
    @RetrofitDefault
    fun provideRetrofitInstance(@DefaultClient defaultHttpClient: OkHttpClient,
                                preferenceManager: AppPreferenceImpl
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(preferenceManager.baseUrl?:Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(defaultHttpClient)
            .build()


    // Api Services
    @Provides
    @Singleton
    fun provideRestApis(@RetrofitDefault retrofit: Retrofit): GeneralApiService =
        retrofit.create(GeneralApiService::class.java)


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RetrofitDefault


}
