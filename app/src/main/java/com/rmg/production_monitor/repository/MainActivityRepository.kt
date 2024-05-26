package com.rmg.production_monitor.repository

interface MainActivityRepository {
    fun clearSession()

    fun getSliderValue(): Boolean
    fun saveSliderValue(value: Boolean)


    fun getLine():Int?
}