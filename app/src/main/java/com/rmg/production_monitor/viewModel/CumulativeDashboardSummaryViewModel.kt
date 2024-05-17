package com.rmg.production_monitor.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmg.production_monitor.core.managers.session.SessionManager
import com.rmg.production_monitor.repository.CumulativeDashboardSummaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CumulativeDashboardSummaryViewModel @Inject constructor(
    private val cumulativeDashboardSummaryRepository: CumulativeDashboardSummaryRepository,
    val session: SessionManager
) : ViewModel() {

    // Cumulative Dashboard Summary
    val cumulativeDashboardSummaryLiveData get() = cumulativeDashboardSummaryRepository.cumulativeDashboardSummaryLiveData

    fun getCumulativeDashboardSummary(lineId: Int) {
        viewModelScope.launch {
            cumulativeDashboardSummaryRepository.getCumulativeDashboardSummary(lineId)
        }
    }

    fun getLineId():String?{
        val lineId= session.fetchLine()
        return lineId
    }


    fun clearSession(){
        val lineId= session.clearSession()

    }
}