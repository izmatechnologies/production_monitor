package com.rmg.production_monitor.core.di

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
    fun provideQualityRepository(qualityRepositoryImpl: QualityRepositoryImpl): QualityRepository

}