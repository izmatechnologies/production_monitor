package com.rmg.production_monitor.broadCastCallReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.rmg.production_monitor.models.local.dao.CumulativeDashBoardDao
import com.rmg.production_monitor.models.local.dao.WIPAnalyticsDao
import com.rmg.production_monitor.models.local.entity.CumulativeDashBoardEntity
import com.rmg.production_monitor.models.local.entity.WIPAnalyticsEntity
import com.rmg.production_monitor.repository.CumulativeDashboardSummaryRepositoryImpl
import com.rmg.production_monitor.repository.DashboardRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WIPAnalyticsApiCallReceiver :BroadcastReceiver(){
    @Inject
    lateinit var dashboardRepositoryImpl: DashboardRepositoryImpl
    @Inject
    lateinit var wipAnalyticsDao: WIPAnalyticsDao
    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            val lineId=intent?.getStringExtra("LINE_ID")

            val response=dashboardRepositoryImpl.getDashboardAnalytics(lineId?.toInt()?:-1)

            if (response.success){
                wipAnalyticsDao.insertWIPDAta(WIPAnalyticsEntity(0,response.payload))
            }

        }

    }
}