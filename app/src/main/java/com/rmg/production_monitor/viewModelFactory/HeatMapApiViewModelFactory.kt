package com.rmg.production_monitor.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rmg.production_monitor.repository.demo.QualityDemoRepository
import com.rmg.production_monitor.viewModel.demo.QualityDemoViewModel

class HeatMapApiViewModelFactory(private val repository: QualityDemoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QualityDemoViewModel::class.java)) {
            return QualityDemoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}