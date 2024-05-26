package com.rmg.production_monitor.view.fragment


import android.view.LayoutInflater
import androidx.fragment.app.viewModels

import com.rmg.production_monitor.view.adapter.PCBAdapter
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.data.NetworkResult

import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.FragmentPCBBinding
import com.rmg.production_monitor.models.remote.CumulativeDashboardDetail.CumulativeDashboardDetailPayload
import com.rmg.production_monitor.models.remote.CumulativeDashboardDetail.HourlyDetail

import com.rmg.production_monitor.view.activity.MainActivity

import com.rmg.production_monitor.viewModel.CumulativeDashboardDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PCBFragment : BaseFragment<FragmentPCBBinding>() {

    private val cumulativeDashboardDetailViewModel by viewModels<CumulativeDashboardDetailViewModel>()
    private lateinit var cumulativeDashboardDetailPayload: CumulativeDashboardDetailPayload
    private var hourlyDetailList = mutableListOf<HourlyDetail>()
    private var flag:Boolean=false
    var lineId =  0

    override fun getViewBinding(inflater: LayoutInflater): FragmentPCBBinding {
        return FragmentPCBBinding.inflate(inflater)
    }

    override fun callInitialApi() {
        super.callInitialApi()
        lineId = cumulativeDashboardDetailViewModel.getLineId()?.toInt() ?: 0
        networkChecker {
            cumulativeDashboardDetailViewModel.getCumulativeDashboardDetail(lineId)
        }
    }

    override fun setupObserver() {
        super.setupObserver()

        cumulativeDashboardDetailViewModel.cumulativeDashboardDetailLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    hideLoader()
                    it.data?.payload?.let { payload ->
                        cumulativeDashboardDetailPayload = payload
                        initializeData()
                    }
                }

                is NetworkResult.Error -> {
                    hideLoader()
                    it.message.toString().toast()
                }

                is NetworkResult.Loading -> {
                    showLoader()
                }

                is NetworkResult.SessionOut -> {
                    showTokenExpiredToast()
                }
                else ->{

                }
            }
        }
    }

    override fun initializeData() {
        super.initializeData()

        if (::cumulativeDashboardDetailPayload.isInitialized) {
            "Style - ${cumulativeDashboardDetailPayload.styleName}".also { binding.textViewStyle.text = it }
            "Color - ${cumulativeDashboardDetailPayload.colorName}".also { binding.textViewColor.text = it }
            "Buyer - ${cumulativeDashboardDetailPayload.buyerName}".also { binding.textViewBuyer.text = it }
            "PO - ${cumulativeDashboardDetailPayload.poNumber}".also { binding.textViewPO.text = it }
            "Hour ${cumulativeDashboardDetailPayload.hour}".also { binding.textViewHour.text = it }
            hourlyDetailList = cumulativeDashboardDetailPayload.hourlyDetails.toMutableList()
            setUpRecycleView()
        }

    }

    override fun setUpRecycleView() {
        super.setUpRecycleView()

        val pcbAdapter = PCBAdapter(hourlyDetailList)
        binding.recyclerView.adapter = pcbAdapter
    }


    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).binding.imgBtnRefresh.setOnClickListener {

            lineId = cumulativeDashboardDetailViewModel.getLineId()?.toInt() ?: 0
            networkChecker {
                cumulativeDashboardDetailViewModel.getCumulativeDashboardDetail(lineId)
            }
        }
    }






}