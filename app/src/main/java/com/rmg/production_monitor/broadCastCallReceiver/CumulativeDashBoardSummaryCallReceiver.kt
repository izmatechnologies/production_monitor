package com.rmg.production_monitor.broadCastCallReceiver

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.rmg.production_monitor.models.local.entity.CumulativeDashBoardEntity
import com.rmg.production_monitor.models.local.viewModel.CumulativeDashBoardLocalViewModel
import com.rmg.production_monitor.repository.CumulativeDashboardSummaryRepositoryImpl
import com.rmg.production_monitor.viewModelFactory.DashBoardSummaryLocalViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CumulativeDashBoardSummaryCallReceiver :BroadcastReceiver(){
    @Inject
    lateinit var cumulativeDashboardSummaryRepositoryImpl: CumulativeDashboardSummaryRepositoryImpl
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null){
            val application = context.applicationContext as Application

            // Create ViewModel using factory
            val viewModelFactory = DashBoardSummaryLocalViewModelFactory(application)
            val cumulativeDashBoardViewModel = ViewModelProvider(ViewModelStore(), viewModelFactory)[CumulativeDashBoardLocalViewModel::class.java]

            CoroutineScope(Dispatchers.IO).launch {
                val lineId=intent?.getStringExtra("LINE_ID")

                val response=cumulativeDashboardSummaryRepositoryImpl.getCumulativeDashboardSummary(lineId?.toInt()?:-1)

                if (response.success){
                    cumulativeDashBoardViewModel.insertCumulativeDashBoardData(CumulativeDashBoardEntity(0,response.payload))
                }

            }
        }

    }
}