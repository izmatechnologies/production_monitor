package com.rmg.production_monitor.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rmg.production_monitor.core.Constants
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.managers.session.SessionManager
import com.rmg.production_monitor.core.service.ApiService
import com.rmg.production_monitor.models.remote.quality.QualityModel
import org.json.JSONObject
import javax.inject.Inject

class MainActivityRepositoryImpl @Inject constructor(
    private val sessionManager: SessionManager
) : MainActivityRepository {
    // Heat map


    // Clear Session
    override fun clearSession() {
        sessionManager.clearSession()
    }
}