package com.rmg.production_monitor.viewModel.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmg.production_monitor.models.remote.quality.QualityModel
import com.rmg.production_monitor.repository.MainActivityRepository
import com.rmg.production_monitor.repository.QualityRepository
import com.rmg.production_monitor.repository.demo.QualityDemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QualityDemoViewModel @Inject constructor(
    private val qualityRepository: QualityDemoRepository,
) : ViewModel() {
    // Heat map
//    val heatMapLiveData get() = qualityRepository.heatMapLiveData
//
//    fun getHeatmap(lineId: Int) {
//        viewModelScope.launch {
//            qualityRepository.getHeatmap(lineId)
//        }
//    }
    private val _apiData = MutableLiveData<QualityModel>()
    val heatMapLiveData: LiveData<QualityModel> get() = _apiData

    fun getHeatmap(lineId: Int) = viewModelScope.launch {
        val response = qualityRepository.getHeatmap(lineId)
        if (response.isSuccessful) {
            _apiData.postValue(response.body())
        }
    }

}