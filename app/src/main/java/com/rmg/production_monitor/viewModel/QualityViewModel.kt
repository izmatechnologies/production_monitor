package com.rmg.production_monitor.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.models.remote.quality.QualityModel
import com.rmg.production_monitor.repository.MainActivityRepository
import com.rmg.production_monitor.repository.QualityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QualityViewModel @Inject constructor(
    private val qualityRepository: QualityRepository,
    private val mainActivityRepository: MainActivityRepository
) : ViewModel() {
    // Heat map
    val heatMapLiveData  = MutableLiveData<QualityModel>()

    fun getHeatmap(lineId: Int) {
        viewModelScope.launch {
            heatMapLiveData.postValue(qualityRepository.getHeatmap(lineId))
        }
    }

    // Clear Session
    fun clearSession(){
        mainActivityRepository.clearSession()
    }

    fun getLineId(): Int? {
        return mainActivityRepository.getLine()
    }


}