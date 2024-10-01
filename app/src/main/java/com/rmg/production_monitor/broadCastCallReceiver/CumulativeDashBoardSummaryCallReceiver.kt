package com.rmg.production_monitor.broadCastCallReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.rmg.production_monitor.models.local.dao.CumulativeDashBoardDao
import com.rmg.production_monitor.models.local.entity.CumulativeDashBoardEntity
import com.rmg.production_monitor.repository.CumulativeDashboardSummaryRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CumulativeDashBoardSummaryCallReceiver :BroadcastReceiver(){
    @Inject
    lateinit var cumulativeDashboardSummaryRepositoryImpl: CumulativeDashboardSummaryRepositoryImpl
    @Inject
    lateinit var CumulativeDashBoardDao: CumulativeDashBoardDao
    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            val lineId=intent?.getStringExtra("LINE_ID")

            val response=cumulativeDashboardSummaryRepositoryImpl.getCumulativeDashboardSummary(lineId?.toInt()?:-1)

            if (response.success){
                CumulativeDashBoardDao.insertDashBoardData(CumulativeDashBoardEntity(0,response.payload))
            }

        }

    }
}