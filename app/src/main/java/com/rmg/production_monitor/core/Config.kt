package com.rmg.production_monitor.core



object  Config {

    const val Production_URL = "http://118.67.215.180:82/"
    const val dev_URL = "https://rmgapi.mahfuj.site/"
    const val BASE_URL = Production_URL
    const val INTERVAL_SECOND:Long=5
    const val SCREEN_ROTATION_INTERVAL:Long=INTERVAL_SECOND*1000
    //const val BASE_URL = BuildConfig.






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