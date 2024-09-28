package com.rmg.production_monitor.service.broadCastCallReceiver

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.rmg.production_monitor.viewModel.QualityViewModel
import com.rmg.production_monitor.viewModel.demo.QualityDemoViewModel
import com.rmg.production_monitor.workManager.HeatMapApiCallWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HeatmapCallReceiver :BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
//        when(intent?.action){
//            "heatmap_api"->{
////                val qualityViewModel=ViewModelProvider.AndroidViewModelFactory.getInstance(context?.applicationContext as Application).create(QualityViewModel::class.java)
//
//            }
//        }

//        val qualityViewModel = ViewModelProvider(context as ViewModelStoreOwner)[QualityDemoViewModel::class.java]
        val qualityViewModel=ViewModelProvider.AndroidViewModelFactory.getInstance(context?.applicationContext as Application).create(QualityDemoViewModel::class.java)
        val lineId = intent?.getIntExtra("LINE_ID", -1) ?: -1
        qualityViewModel.getHeatmap(lineId)

//        if (context != null) {
//            val requestId = intent?.getStringExtra("REQUEST_ID") ?: return
//            val workManager = WorkManager.getInstance(context)
//
//            val inputData = workDataOf("REQUEST_ID" to requestId)
//
//            val workRequest = OneTimeWorkRequestBuilder<HeatMapApiCallWorker>()
//                .setInputData(inputData)
//                .build()
//
//            workManager.enqueue(workRequest)
//
////            scheduleApiWork(context, 0)  // Immediate work after alarm triggers
//        }



    }

    private fun scheduleApiWork(context: Context, delayInMinutes: Long) {
        val workRequest = OneTimeWorkRequestBuilder<HeatMapApiCallWorker>()
            .setInitialDelay(delayInMinutes, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}