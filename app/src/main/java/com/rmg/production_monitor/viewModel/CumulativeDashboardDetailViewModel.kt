package com.rmg.production_monitor.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.rmg.production_monitor.repository.CumulativeDashboardDetailRepository
import com.rmg.production_monitor.repository.MainActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CumulativeDashboardDetailViewModel @Inject constructor(
    private val cumulativeDashboardDetailRepository: CumulativeDashboardDetailRepository,
    private val mainActivityRepository: MainActivityRepository
) : ViewModel() {

    // Cumulative Dashboard Detail
    val cumulativeDashboardDetailLiveData get() = cumulativeDashboardDetailRepository.cumulativeDashboardDetailLiveData

    fun getCumulativeDashboardDetail(lineId: Int) {
        viewModelScope.launch {
            cumulativeDashboardDetailRepository.getCumulativeDashboardDetail(lineId)
        }
    }

    fun getLineId(): Int? {
        return mainActivityRepository.getLine()
    }
fun clearSession(){
    mainActivityRepository.clearSession()
}
}