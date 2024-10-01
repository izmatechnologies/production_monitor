package com.rmg.production_monitor.models.local.repository

import androidx.lifecycle.LiveData
import com.rmg.production_monitor.models.local.dao.PCBDashboardDetailsDao
import com.rmg.production_monitor.models.local.entity.PCBDashBoardDetailsEntity
import javax.inject.Inject

class PCBDashboardDetailsLocalRepository @Inject constructor(
    private val pcbDashboardDetailsDao: PCBDashboardDetailsDao
) {
    val inputPCBDashBoardDetailsData:LiveData<PCBDashBoardDetailsEntity> = pcbDashboardDetailsDao.getPCBDashboardDetails()

    fun insertPCBDashboardData(pcbDashBoardDetailsEntity: PCBDashBoardDetailsEntity){
        pcbDashboardDetailsDao.insertPCBDashBoardDetailsData(pcbDashBoardDetailsEntity)
    }
}