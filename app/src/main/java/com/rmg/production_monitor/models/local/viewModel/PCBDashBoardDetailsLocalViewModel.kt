package com.rmg.production_monitor.models.local.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rmg.production_monitor.core.db.ApiResponseStoreDataBase
import com.rmg.production_monitor.models.local.entity.PCBDashBoardDetailsEntity
import com.rmg.production_monitor.models.local.repository.PCBDashboardDetailsLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PCBDashBoardDetailsLocalViewModel(application: Application):AndroidViewModel(application) {
    val getPCBDashBoardDetailsList:LiveData<PCBDashBoardDetailsEntity>
    private val getPCBDashboardDetailsLocalRepository:PCBDashboardDetailsLocalRepository

    init {
        val pcbDashboardDetailsDao=ApiResponseStoreDataBase.getInstance(application).getPCBDashboardDetailsDao()
        getPCBDashboardDetailsLocalRepository=PCBDashboardDetailsLocalRepository(pcbDashboardDetailsDao)
        getPCBDashBoardDetailsList=getPCBDashboardDetailsLocalRepository.inputPCBDashBoardDetailsData

    }

    fun insertCumulativeDashBoardData(pcbDashBoardDetailsEntity: PCBDashBoardDetailsEntity){
        viewModelScope.launch(Dispatchers.IO) {
            getPCBDashboardDetailsLocalRepository.insertPCBDashboardData(pcbDashBoardDetailsEntity)
        }
    }
}