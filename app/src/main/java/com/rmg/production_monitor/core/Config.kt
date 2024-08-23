package com.rmg.production_monitor.core

import android.util.Log
import com.rmg.production_monitor.core.extention.log
import com.rmg.production_monitor.core.managers.preference.AppPreferenceImpl


object  Config {

     val Stataging_URL = "http://118.67.215.180:82/"
     val Production_URL = "http://192.168.5.233:88/"
     val dev_URL = "https://rmgapi.mahfuj.site/"
//    const val BASE_URL = Production_URL
    const val INTERVAL_SECOND:Long=30
    const val SCREEN_ROTATION_INTERVAL:Long=INTERVAL_SECOND*1000
    //const val BASE_URL = BuildConfig.

    private lateinit var appPreferenceImpl: AppPreferenceImpl

    var BASE_URL = Stataging_URL

    fun init(appPreferenceImpl: AppPreferenceImpl) {
        this.appPreferenceImpl = appPreferenceImpl
        val preferenceBaseUrl = appPreferenceImpl.baseUrl
        if (!preferenceBaseUrl.isNullOrEmpty()){
            BASE_URL = appPreferenceImpl.baseUrl.toString()
        }else{
            BASE_URL = Stataging_URL
        }
        "BASE_URL to show $BASE_URL".log("192_dim")
    }





    object Storage{
        const val APPLICATION_DATABASE_NAME = "QC_APP_DB.sqlite3"
        const val APPLICATION_DATABASE_VERSION = 1

        const val APPLICATION_PREFERENCE_NAME = "qc_session"
    }

    object OperationModes{
        const val QC_OPERATION_MODE = "qc_operation"
        const val SEWING_IN_LINE_OPERATION_MODE = "sewing_in_line_operation"


    }

    const val CORE_OPERATION_MODE = OperationModes.QC_OPERATION_MODE
}