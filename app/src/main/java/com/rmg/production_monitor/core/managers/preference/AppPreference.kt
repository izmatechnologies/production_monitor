package com.rmg.production_monitor.core.managers.preference

interface AppPreference {

    fun storeSting(key: String, value: String?)
    fun remove(key: String)

    fun getSting(key: String, defaultValue: String?): String?

    fun storeInt(key: String, value: Int)

    fun getInt(key: String, defaultValue: Int): Int

    fun saveSliderValue(value: Boolean)
    fun getSliderValue(): Boolean

}