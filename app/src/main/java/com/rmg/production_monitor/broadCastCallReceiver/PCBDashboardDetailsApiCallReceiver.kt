package com.rmg.production_monitor.broadCastCallReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.rmg.production_monitor.models.local.dao.CumulativeDashBoardDao
import com.rmg.production_monitor.models.local.dao.PCBDashboardDetailsDao
import com.rmg.production_monitor.models.local.dao.WIPAnalyticsDao
import com.rmg.production_monitor.models.local.entity.CumulativeDashBoardEntity
import com.rmg.production_monitor.models.local.entity.PCBDashBoardDetailsEntity
import com.rmg.production_monitor.models.local.entity.WIPAnalyticsEntity
import com.rmg.production_monitor.repository.CumulativeDashboardDetailRepositoryImpl
import com.rmg.production_monitor.repository.CumulativeDashboardSummaryRepositoryImpl
import com.rmg.production_monitor.repository.DashboardRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PCBDashboardDetailsApiCallReceiver :BroadcastReceiver(){
    @Inject
    lateinit var CumulativeDashboardDetailRepositoryImpl: CumulativeDashboardDetailRepositoryImpl
    @Inject
    lateinit var pcbDashboardDetailsDao: PCBDashboardDetailsDao
    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            val lineId=intent?.getStringExtra("LINE_ID")

            val response=CumulativeDashboardDetailRepositoryImpl.getCumulativeDashboardDetail(lineId?.toInt()?:-1)

            if (response.success == true){
                pcbDashboardDetailsDao.insertPCBDashBoardDetailsData(PCBDashBoardDetailsEntity(0,response.payload))
            }

        }

    }
}