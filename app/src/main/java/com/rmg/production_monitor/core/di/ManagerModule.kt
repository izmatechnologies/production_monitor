package com.rmg.production_monitor.core.di




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