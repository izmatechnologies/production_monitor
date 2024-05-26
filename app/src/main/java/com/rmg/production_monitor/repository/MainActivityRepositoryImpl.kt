package com.rmg.production_monitor.repository


import com.rmg.production_monitor.core.managers.preference.AppPreferenceImpl

import org.json.JSONObject
import java.util.prefs.Preferences
import javax.inject.Inject

class MainActivityRepositoryImpl @Inject constructor(
    private val prefs: AppPreferenceImpl,
) : MainActivityRepository {
    // Heat map


    override fun clearSession() {
        prefs.clearAll()
    }

    override fun getSliderValue(): Boolean {
     return   prefs.isPageFreezeFlag
    }

    override fun saveSliderValue(value: Boolean) {
        prefs.isPageFreezeFlag=value
    }

    override fun getLine(): Int? {
        return prefs.lineId
    }
}