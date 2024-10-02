package com.rmg.production_monitor.broadCastCallReceiver

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.google.gson.Gson
import com.rmg.production_monitor.models.local.entity.HeatMapEntity
import com.rmg.production_monitor.models.local.repository.HeatMapLocalRepository
import com.rmg.production_monitor.models.local.viewModel.HeatmapLocalViewModel
import com.rmg.production_monitor.repository.QualityRepositoryImpl
import com.rmg.production_monitor.viewModelFactory.HeatmapLocalViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HeatmapCallReceiver :BroadcastReceiver(){
    @Inject
    lateinit var qualityRepositoryImpl: QualityRepositoryImpl
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null){
            val application = context.applicationContext as Application

            // Create ViewModel using factory
            val viewModelFactory = HeatmapLocalViewModelFactory(application)
            val heatmapLocalViewModel = ViewModelProvider(ViewModelStore(), viewModelFactory)[HeatmapLocalViewModel::class.java]

            CoroutineScope(Dispatchers.IO).launch {
                val lineId=intent?.getStringExtra("LINE_ID")
                val response=qualityRepositoryImpl.getHeatmap(lineId?.toInt()?:-1)

                if (response.success){
                    heatmapLocalViewModel.insertHeatmapData(HeatMapEntity(0,response.payload))
                    Log.d("heatMapDao", "onReceive:${Gson().toJson(response.payload.stationWiseDhus)} ")
                }

            }
        }

    }
}