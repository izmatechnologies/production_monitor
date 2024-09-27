package com.rmg.production_monitor.models.local.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rmg.production_monitor.core.db.ApiResponseStoreDataBase
import com.rmg.production_monitor.core.db.AppDatabase
import com.rmg.production_monitor.models.local.entity.HeatMapEntity
import com.rmg.production_monitor.models.local.repository.HeatMapLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HeatmapLocalViewModel(application: Application) : AndroidViewModel(application) {
    val getHeatmapList: LiveData<HeatMapEntity>

    private val heatMapLocalRepository: HeatMapLocalRepository

    init {
        val heatMapDao = ApiResponseStoreDataBase.getInstance(application).getHeatMapDao()
        heatMapLocalRepository = HeatMapLocalRepository(heatMapDao)
        getHeatmapList = heatMapLocalRepository.inputHeatmapData
    }

    fun insertHeatmapData(heatMapEntity: HeatMapEntity){
        viewModelScope.launch(Dispatchers.IO) {
            heatMapLocalRepository.insertHeatMapData(heatMapEntity)
        }
    }
}