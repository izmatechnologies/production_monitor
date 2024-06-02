package com.rmg.production_monitor.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmg.production_monitor.repository.DashboardRepository
import com.rmg.production_monitor.repository.MainActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private  val repository:DashboardRepository,  private val mainActivityRepository: MainActivityRepository):ViewModel() {
    val dashboardAnalyticLiveData get() = repository.dashboardAnalyticsData

    fun getDashboardAnalytics(lineId:Int) {
        viewModelScope.launch {
            repository.getDashboardAnalytics(lineId)
        }
    }


    fun getLineId(): Int? {
        return mainActivityRepository.getLine()
    }

    fun clearSession(){

        mainActivityRepository.clearSession()
    }


}