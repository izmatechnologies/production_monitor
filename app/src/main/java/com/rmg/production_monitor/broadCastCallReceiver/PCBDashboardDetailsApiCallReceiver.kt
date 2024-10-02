package com.rmg.production_monitor.broadCastCallReceiver

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.rmg.production_monitor.models.local.dao.CumulativeDashBoardDao
import com.rmg.production_monitor.models.local.dao.PCBDashboardDetailsDao
import com.rmg.production_monitor.models.local.dao.WIPAnalyticsDao
import com.rmg.production_monitor.models.local.entity.CumulativeDashBoardEntity
import com.rmg.production_monitor.models.local.entity.PCBDashBoardDetailsEntity
import com.rmg.production_monitor.models.local.entity.WIPAnalyticsEntity
import com.rmg.production_monitor.models.local.repository.PCBDashboardDetailsLocalRepository
import com.rmg.production_monitor.models.local.viewModel.HeatmapLocalViewModel
import com.rmg.production_monitor.models.local.viewModel.PCBDashBoardDetailsLocalViewModel
import com.rmg.production_monitor.repository.CumulativeDashboardDetailRepositoryImpl
import com.rmg.production_monitor.repository.CumulativeDashboardSummaryRepositoryImpl
import com.rmg.production_monitor.repository.DashboardRepositoryImpl
import com.rmg.production_monitor.viewModelFactory.HeatmapLocalViewModelFactory
import com.rmg.production_monitor.viewModelFactory.PCBDashBoardDetailsLocalViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PCBDashboardDetailsApiCallReceiver :BroadcastReceiver(){
    @Inject
    lateinit var CumulativeDashboardDetailRepositoryImpl: CumulativeDashboardDetailRepositoryImpl
    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            if (context !=null){
                val application = context.applicationContext as Application

                // Create ViewModel using factory
                val viewModelFactory = PCBDashBoardDetailsLocalViewModelFactory(application)
                val pcbDashboardDetailsViewModel = ViewModelProvider(ViewModelStore(), viewModelFactory)[PCBDashBoardDetailsLocalViewModel::class.java]

                val lineId=intent?.getStringExtra("LINE_ID")

                val response=CumulativeDashboardDetailRepositoryImpl.getCumulativeDashboardDetail(lineId?.toInt()?:-1)

                if (response.success == true){
                    pcbDashboardDetailsViewModel.insertCumulativeDashBoardData(PCBDashBoardDetailsEntity(0,response.payload))
                }
            }

        }

    }
}