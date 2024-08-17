package com.rmg.production_monitor.core

import android.app.Application
import android.content.Context
import com.rmg.production_monitor.core.managers.preference.AppPreferenceImpl
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class ProductionMonitorApplication : Application() {


    @Inject
    lateinit var appPreferenceImpl: AppPreferenceImpl
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
        Config.init(appPreferenceImpl)
    }
}