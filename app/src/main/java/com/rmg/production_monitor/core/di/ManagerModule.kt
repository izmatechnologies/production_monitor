package com.faisal.quc.core.di

import com.faisal.quc.core.managers.NetworkManager
import com.faisal.quc.core.managers.NetworkManagerImpl
import com.faisal.quc.core.managers.preference.AppPreference
import com.faisal.quc.core.managers.preference.AppPreferenceImpl
import com.faisal.quc.repository.MarkingImageRepository
import com.faisal.quc.repository.MarkingImageRepositoryImpl
import com.faisal.quc.repository.AuthenticationRepository
import com.faisal.quc.repository.AuthenticationRepositoryImpl
import com.faisal.quc.core.managers.session.SessionManager
import com.faisal.quc.core.managers.session.SessionManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ManagerModule {

    @Binds
    fun provideSessionManager(sessionManagerImpl: SessionManagerImpl): SessionManager


    @Binds
    fun providePreference(appPreferenceImpl: AppPreferenceImpl): AppPreference

    @Binds
    fun provideNetworkManage(networkManagerImpl: NetworkManagerImpl): NetworkManager

}