package com.rmg.production_monitor.broadCastCallReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.rmg.production_monitor.models.local.dao.HeatMapDao
import com.rmg.production_monitor.models.local.entity.HeatMapEntity
import com.rmg.production_monitor.repository.QualityRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HeatmapCallReceiver :BroadcastReceiver(){
    @Inject
    lateinit var qualityRepositoryImpl: QualityRepositoryImpl
    @Inject
    lateinit var heatMapDao: HeatMapDao
    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            val lineId=intent?.getStringExtra("LINE_ID")

            val response=qualityRepositoryImpl.getHeatmap(lineId?.toInt()?:-1)

            if (response.success){
                heatMapDao.insertHeatMapData(HeatMapEntity(0,response.payload))
            }

        }

    }
}