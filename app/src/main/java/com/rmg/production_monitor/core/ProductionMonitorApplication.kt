package com.rmg.production_monitor.core

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ProductionMonitorApplication : Application() {


    companion object {

        private var _mApplication: Application? = null
        private fun getmApplication(): Application? {
            return _mApplication
        }

        fun getContext(): Context {
            return getmApplication()!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        _mApplication = this
    }
}