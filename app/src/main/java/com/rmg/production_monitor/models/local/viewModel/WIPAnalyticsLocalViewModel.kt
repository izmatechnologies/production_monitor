package com.rmg.production_monitor.models.local.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rmg.production_monitor.core.db.ApiResponseStoreDataBase
import com.rmg.production_monitor.models.local.entity.WIPAnalyticsEntity
import com.rmg.production_monitor.models.local.repository.WIPAnalyticsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WIPAnalyticsLocalViewModel(application: Application) : AndroidViewModel(application) {
    val getWIPListData: LiveData<WIPAnalyticsEntity>

    private val wipAnalyticsRepository: WIPAnalyticsRepository

    init {
        val wipAnalyticsDao = ApiResponseStoreDataBase.getInstance(application).getWIPAnalyticsDao()
        wipAnalyticsRepository = WIPAnalyticsRepository(wipAnalyticsDao)
        getWIPListData = wipAnalyticsRepository.inputWIPData
    }

    fun insertWipAnalyticsData(wipAnalyticsEntity: WIPAnalyticsEntity){
        viewModelScope.launch(Dispatchers.IO) {
            wipAnalyticsRepository.insertWIPData(wipAnalyticsEntity)
        }
    }
}