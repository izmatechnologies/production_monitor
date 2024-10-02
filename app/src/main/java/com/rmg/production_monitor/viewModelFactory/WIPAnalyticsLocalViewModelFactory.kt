package com.rmg.production_monitor.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rmg.production_monitor.models.local.viewModel.WIPAnalyticsLocalViewModel

class WIPAnalyticsLocalViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WIPAnalyticsLocalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WIPAnalyticsLocalViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}