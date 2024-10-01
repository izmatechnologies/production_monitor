package com.rmg.production_monitor.core.di

import com.rmg.production_monitor.repository.AuthenticationRepository
import com.rmg.production_monitor.repository.AuthenticationRepositoryImpl
import com.rmg.production_monitor.repository.CumulativeDashboardDetailRepository
import com.rmg.production_monitor.repository.CumulativeDashboardDetailRepositoryImpl
import com.rmg.production_monitor.repository.CumulativeDashboardSummaryRepository
import com.rmg.production_monitor.repository.CumulativeDashboardSummaryRepositoryImpl
import com.rmg.production_monitor.repository.DashboardRepository
import com.rmg.production_monitor.repository.DashboardRepositoryImpl
import com.rmg.production_monitor.repository.MainActivityRepository
import com.rmg.production_monitor.repository.MainActivityRepositoryImpl
import com.rmg.production_monitor.repository.QualityRepository
import com.rmg.production_monitor.repository.QualityRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// todo
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    //    @Binds
//    fun provideAuthenticationRepository(authenticationRepositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository
    @Binds
    fun provideMainRepository(mainRepositoryImpl: MainActivityRepositoryImpl): MainActivityRepository

    @Binds
    fun provideQualityRepository(qualityRepositoryImpl: QualityRepositoryImpl): QualityRepository

    @Binds
    fun provideAuthRepository(authRepositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    fun provideDashboardAnalyticsRepository(dashboardAuthRepositoryImpl: DashboardRepositoryImpl): DashboardRepository

    @Binds
    fun provideCumulativeDashboardSummaryRepository(cumulativeDashboardSummaryRepositoryImpl: CumulativeDashboardSummaryRepositoryImpl): CumulativeDashboardSummaryRepository

    @Binds
    fun provideCumulativeDashboardDetailRepository(cumulativeDashboardDetailRepositoryImpl: CumulativeDashboardDetailRepositoryImpl): CumulativeDashboardDetailRepository

}