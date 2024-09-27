package com.rmg.production_monitor.models.local.repository

import androidx.lifecycle.LiveData
import com.rmg.production_monitor.models.local.dao.HeatMapDao
import com.rmg.production_monitor.models.local.entity.HeatMapEntity
import javax.inject.Inject

class HeatMapLocalRepository @Inject constructor(
    private val heatMapDao: HeatMapDao
) {
    val inputHeatmapData:LiveData<HeatMapEntity> = heatMapDao.getHeatMapList()

    fun insertHeatMapData(heatMapEntity: HeatMapEntity){
        heatMapDao.insertHeatMapData(heatMapEntity)
    }

}