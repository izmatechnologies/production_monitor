package com.rmg.production_monitor.repository

import androidx.lifecycle.LiveData
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.models.remote.quality.QualityModel

interface QualityRepository {
    // Heat map
    val heatMapLiveData: LiveData<NetworkResult<QualityModel>>

    suspend fun getHeatmap()

    // Clear Session
    fun clearSession()
}