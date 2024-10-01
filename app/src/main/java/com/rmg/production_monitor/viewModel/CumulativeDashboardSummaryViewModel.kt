package com.rmg.production_monitor.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmg.production_monitor.models.remote.cumulativeDashboardSummary.CumulativeDashboardSummaryModel
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
    val cumulativeDashboardSummaryLiveData = MutableLiveData<CumulativeDashboardSummaryModel>()

    fun getCumulativeDashboardSummary(lineId: Int) {
        viewModelScope.launch {
            cumulativeDashboardSummaryLiveData.postValue(cumulativeDashboardSummaryRepository.getCumulativeDashboardSummary(lineId))
        }
    }

    fun getLineId(): Int? {
        return mainActivityRepository.getLine()
    }

fun clearSelection(){
    mainActivityRepository.clearSession()
}


}