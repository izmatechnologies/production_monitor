package com.rmg.production_monitor.view.fragment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.rmg.production_monitor.R
import com.rmg.production_monitor.view.adapter.PCBAdapter
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.extention.log
import com.rmg.production_monitor.core.extention.showLogoutDialog
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.FragmentPCBBinding
import com.rmg.production_monitor.models.remote.CumulativeDashboardDetail.CumulativeDashboardDetailPayload
import com.rmg.production_monitor.models.remote.CumulativeDashboardDetail.HourlyDetail
import com.rmg.production_monitor.view.activity.LoginActivity
import com.rmg.production_monitor.view.activity.MainActivity
import com.rmg.production_monitor.view.activity.ToolbarInterface
import com.rmg.production_monitor.viewModel.CumulativeDashboardDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PCBFragment : BaseFragment<FragmentPCBBinding>(),ToolbarInterface {

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

    override fun onRefreshButtonClick() {
        "toast".toast(requireContext())
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            (requireActivity() as MainActivity).setOnToolBarListener(this)
        } catch (ex: Exception) {
            ex.printStackTrace()
            ex.toString().log("dim")
        }
    }
}