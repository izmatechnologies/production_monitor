package com.faisal.quc.core.di

import android.app.Application
import com.faisal.quc.core.db.AppDatabase
import com.faisal.quc.models.local.dao.QcIssueDao
import com.faisal.quc.models.local.dao.QcOperationDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun getAppDatabase(application: Application): AppDatabase {
        return AppDatabase.getAppDBInstance(application)
    }



    @Provides
    @Singleton
    fun provideQcOperationDao(appDatabase: AppDatabase): QcOperationDao {
        return appDatabase.getQcOperationDao()
    }

    @Provides
    @Singleton
    fun provideQcIssueDao(appDatabase: AppDatabase): QcIssueDao {
        return appDatabase.getQcIssueDao()
    }





    @Singleton
    @Provides
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

}