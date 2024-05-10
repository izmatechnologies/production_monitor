package com.rmg.production_monitor.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmg.production_monitor.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private  val repository:DashboardRepository):ViewModel() {
    val dashboardAnalyticLiveData get() = repository.dashboardAnalyticsData

    fun getDashboardAnalytics() {
        viewModelScope.launch {
            repository.getDashboardAnalytics()
        }
    }
}