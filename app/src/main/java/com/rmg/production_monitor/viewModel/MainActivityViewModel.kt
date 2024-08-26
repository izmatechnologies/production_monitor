package com.rmg.production_monitor.viewModel

import androidx.lifecycle.ViewModel
import com.rmg.production_monitor.core.managers.preference.AppPreferenceImpl
import com.rmg.production_monitor.repository.CumulativeDashboardSummaryRepository
import com.rmg.production_monitor.repository.MainActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mainActivityRepository: MainActivityRepository,
    private val appPreference: AppPreferenceImpl

    ):ViewModel() {


        fun clearSession(){

            mainActivityRepository.clearSession()
        }


    fun getSliding():Boolean{
        return mainActivityRepository.getSliderValue()

    }
    fun saveSliderValue(value:Boolean){
        mainActivityRepository.saveSliderValue(value)
    }

    fun getLineId(): Int? {
        return mainActivityRepository.getLine()
    }

    fun getPlantLineName():String{
        return appPreference.selectedLineName?:""
    }

}