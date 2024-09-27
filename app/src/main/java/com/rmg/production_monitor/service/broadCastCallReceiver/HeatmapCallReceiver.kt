package com.rmg.production_monitor.service.broadCastCallReceiver

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.rmg.production_monitor.viewModel.QualityViewModel

class HeatmapCallReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            "heatmap_api"->{
                val qualityViewModel=ViewModelProvider.AndroidViewModelFactory.getInstance(context?.applicationContext as Application).create(QualityViewModel::class.java)
                val lineId = intent.getIntExtra("LINE_ID", -1) ?: -1
                qualityViewModel.getHeatmap(lineId)
            }
        }

    }
}