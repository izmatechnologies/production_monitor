package com.rmg.production_monitor.core.managers.preference

interface AppPreference {

    fun storeSting(key: String, value: String?)
    fun remove(key: String)

    fun storeInt(key: String, value: Int)
    fun storeBoolean(key: String, value: Boolean)







}