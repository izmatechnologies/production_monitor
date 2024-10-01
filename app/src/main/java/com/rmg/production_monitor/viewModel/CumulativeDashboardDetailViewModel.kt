package com.rmg.production_monitor.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmg.production_monitor.models.remote.cumulativeDashboardDetail.CumulativeDashboardDetailModel

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
    val cumulativeDashboardDetailLiveData =MutableLiveData<CumulativeDashboardDetailModel>()

    fun getCumulativeDashboardDetail(lineId: Int) {
        viewModelScope.launch {
            cumulativeDashboardDetailLiveData.postValue( cumulativeDashboardDetailRepository.getCumulativeDashboardDetail(lineId))

        }
    }

    fun getLineId(): Int? {
        return mainActivityRepository.getLine()
    }
fun clearSession(){
    mainActivityRepository.clearSession()
}
}