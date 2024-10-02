package com.rmg.production_monitor.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rmg.production_monitor.models.local.viewModel.PCBDashBoardDetailsLocalViewModel

class PCBDashBoardDetailsLocalViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PCBDashBoardDetailsLocalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PCBDashBoardDetailsLocalViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}