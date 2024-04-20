package com.rmg.production_monitor

import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.FragmentQualityBinding
import com.rmg.production_monitor.viewModel.QualityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QualityFragment : BaseFragment<FragmentQualityBinding>() {

    private val qualityViewModel by viewModels<QualityViewModel>()

    override fun getViewBinding(inflater: LayoutInflater): FragmentQualityBinding {
        return FragmentQualityBinding.inflate(inflater)
    }

    override fun callInitialApi() {
        super.callInitialApi()
        networkChecker {
            qualityViewModel.getHeatmap()
        }
    }

    override fun setupObserver() {
        super.setupObserver()
        qualityViewModel.heatMapLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    hideLoader()
                    Log.i("Test56", "setupObserver: ${it.data?.payload}")
                }

                is NetworkResult.Error -> {
                    hideLoader()
                    it.message.toString().toast()
                }

                is NetworkResult.Loading -> {
                    showLoader()
                }

                is NetworkResult.SessionOut -> {
                    qualityViewModel.clearSession()
                    showTokenExpiredToast()
                    //(requireActivity() as MainActivity).logout()
                }
            }
        }
    }
}