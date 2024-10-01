package com.rmg.production_monitor.models.local.repository

import androidx.lifecycle.LiveData
import com.rmg.production_monitor.models.local.dao.CumulativeDashBoardDao
import com.rmg.production_monitor.models.local.entity.CumulativeDashBoardEntity
import javax.inject.Inject

class CumulativeDashBoardLocalRepository @Inject constructor(
    private val cumulativeDashBoardDao: CumulativeDashBoardDao
) {
    val inputCumulativeDashboardData:LiveData<CumulativeDashBoardEntity> = cumulativeDashBoardDao.getDashBoardSummary()

    fun insertCumulativeDashBoardData(cumulativeDashBoardEntity: CumulativeDashBoardEntity){
        cumulativeDashBoardDao.insertDashBoardData(cumulativeDashBoardEntity)
    }
}