package com.rmg.production_monitor.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmg.production_monitor.repository.CumulativeDashboardSummaryRepository
import com.rmg.production_monitor.repository.MainActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CumulativeDashboardSummaryViewModel @Inject constructor(
    private val cumulativeDashboardSummaryRepository: CumulativeDashboardSummaryRepository,
    private val mainActivityRepository: MainActivityRepository
) : ViewModel() {

    // Cumulative Dashboard Summary
    val cumulativeDashboardSummaryLiveData get() = cumulativeDashboardSummaryRepository.cumulativeDashboardSummaryLiveData

    fun getCumulativeDashboardSummary(lineId: Int) {
        viewModelScope.launch {
            cumulativeDashboardSummaryRepository.getCumulativeDashboardSummary(lineId)
        }
    }

    fun getLineId(): Int? {
        return mainActivityRepository.getLine()
    }




}