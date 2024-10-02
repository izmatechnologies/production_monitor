package com.rmg.production_monitor.models.local.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rmg.production_monitor.core.db.ApiResponseStoreDataBase
import com.rmg.production_monitor.core.db.AppDatabase
import com.rmg.production_monitor.models.local.entity.CumulativeDashBoardEntity
import com.rmg.production_monitor.models.local.repository.CumulativeDashBoardLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CumulativeDashBoardLocalViewModel(application: Application):AndroidViewModel(application) {
    val getCumulativeDashBoardList:LiveData<CumulativeDashBoardEntity>
    private val getCumulativeDashBoardLocalRepository:CumulativeDashBoardLocalRepository

    init {
        val cumulativeDashBoardDao= AppDatabase.getAppDBInstance(application).getCumulativeDashBoardDao()
        getCumulativeDashBoardLocalRepository=CumulativeDashBoardLocalRepository(cumulativeDashBoardDao)
         getCumulativeDashBoardList=getCumulativeDashBoardLocalRepository.inputCumulativeDashboardData

    }

    fun insertCumulativeDashBoardData(cumulativeDashBoardEntity: CumulativeDashBoardEntity){
        viewModelScope.launch(Dispatchers.IO) {
            getCumulativeDashBoardLocalRepository.insertCumulativeDashBoardData(cumulativeDashBoardEntity)
        }
    }
}