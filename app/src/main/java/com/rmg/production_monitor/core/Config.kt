package com.faisal.quc.core

import com.faisal.quc.BuildConfig

object  Config {

    const val Production_URL = "http://118.67.215.180:82/"
    const val dev_URL = "https://rmgapi.mahfuj.site/"
    const val BASE_URL = Production_URL
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

    const val CORE_OPERATION_MODE =OperationModes.QC_OPERATION_MODE
}