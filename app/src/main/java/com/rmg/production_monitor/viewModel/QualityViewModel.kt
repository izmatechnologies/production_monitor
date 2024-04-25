package com.rmg.production_monitor.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmg.production_monitor.repository.QualityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QualityViewModel @Inject constructor(
    private val qualityRepository: QualityRepository
) : ViewModel() {
    // Heat map
    val heatMapLiveData get() = qualityRepository.heatMapLiveData

    fun getHeatmap(lineId: Int) {
        viewModelScope.launch {
            qualityRepository.getHeatmap(lineId)
        }
    }

    // Clear Session
    fun clearSession(){
        qualityRepository.clearSession()
    }
}