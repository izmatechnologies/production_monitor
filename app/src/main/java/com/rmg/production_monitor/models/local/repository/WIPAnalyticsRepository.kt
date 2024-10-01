package com.rmg.production_monitor.models.local.repository

import androidx.lifecycle.LiveData
import com.rmg.production_monitor.models.local.dao.WIPAnalyticsDao
import com.rmg.production_monitor.models.local.entity.WIPAnalyticsEntity
import javax.inject.Inject

class WIPAnalyticsRepository @Inject constructor(
    private val wipAnalyticsDao: WIPAnalyticsDao
) {
    val inputWIPData:LiveData<WIPAnalyticsEntity> = wipAnalyticsDao.getWIPList()

    fun insertWIPData(wipAnalyticsEntity: WIPAnalyticsEntity){
        wipAnalyticsDao.insertWIPDAta(wipAnalyticsEntity)
    }
}