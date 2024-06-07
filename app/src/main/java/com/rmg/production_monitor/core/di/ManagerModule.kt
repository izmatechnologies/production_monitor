package com.rmg.production_monitor.core.di




import com.rmg.production_monitor.core.managers.network.NetworkManager
import com.rmg.production_monitor.core.managers.network.NetworkManagerImpl
import com.rmg.production_monitor.core.managers.network.PingManager
import com.rmg.production_monitor.core.managers.network.PingManagerImp
import com.rmg.production_monitor.core.managers.preference.AppPreference
import com.rmg.production_monitor.core.managers.preference.AppPreferenceImpl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ManagerModule {




    @Binds
    fun providePreference(appPreferenceImpl: AppPreferenceImpl): AppPreference

    @Binds
    fun provideNetworkManage(networkManagerImpl: NetworkManagerImpl): NetworkManager

    @Binds
    fun providePinManage(pinManagerImp: PingManagerImp): PingManager

}