package com.rmg.production_monitor.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rmg.production_monitor.core.Constants
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.managers.preference.AppPreference
import com.rmg.production_monitor.core.managers.session.SessionManager
import com.rmg.production_monitor.core.service.ApiService
import com.rmg.production_monitor.models.remote.quality.QualityModel
import org.json.JSONObject
import java.util.prefs.Preferences
import javax.inject.Inject

class MainActivityRepositoryImpl @Inject constructor(
    private val sessionManager: SessionManager,private val prefs: AppPreference,
) : MainActivityRepository {
    // Heat map


    // Clear Session
    override fun clearSession() {
        sessionManager.clearSession()
    }

    override fun getSliderValue(): Boolean {
     return   prefs.getSliderValue()
    }

    override fun saveSliderValue(value: Boolean) {
        prefs.saveSliderValue(value)
    }
}