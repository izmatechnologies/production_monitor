package com.rmg.production_monitor.core.di




import com.rmg.production_monitor.core.managers.NetworkManager
import com.rmg.production_monitor.core.managers.NetworkManagerImpl
import com.rmg.production_monitor.core.managers.preference.AppPreference
import com.rmg.production_monitor.core.managers.preference.AppPreferenceImpl
import com.rmg.production_monitor.core.managers.session.SessionManager
import com.rmg.production_monitor.core.managers.session.SessionManagerImpl
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