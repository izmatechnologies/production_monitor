package com.rmg.production_monitor.broadCastCallReceiver

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.rmg.production_monitor.models.local.entity.WIPAnalyticsEntity
import com.rmg.production_monitor.models.local.viewModel.WIPAnalyticsLocalViewModel
import com.rmg.production_monitor.repository.DashboardRepositoryImpl
import com.rmg.production_monitor.viewModelFactory.WIPAnalyticsLocalViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WIPAnalyticsApiCallReceiver :BroadcastReceiver(){
    @Inject
    lateinit var dashboardRepositoryImpl: DashboardRepositoryImpl

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context !=null){
            val application = context.applicationContext as Application

            // Create ViewModel using factory
            val viewModelFactory = WIPAnalyticsLocalViewModelFactory(application)
            val wIPAnalyticsLocalViewModel = ViewModelProvider(ViewModelStore(), viewModelFactory)[WIPAnalyticsLocalViewModel::class.java]

            CoroutineScope(Dispatchers.IO).launch {
                val lineId=intent?.getStringExtra("LINE_ID")

                val response=dashboardRepositoryImpl.getDashboardAnalytics(lineId?.toInt()?:-1)

                if (response.success){
                    wIPAnalyticsLocalViewModel.insertWipAnalyticsData(WIPAnalyticsEntity(0,response.payload))
                }

            }
        }

    }
}