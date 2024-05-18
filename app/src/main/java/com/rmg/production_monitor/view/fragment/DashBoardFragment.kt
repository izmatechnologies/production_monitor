package com.rmg.production_monitor.view.fragment

import android.content.Intent
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.rmg.production_monitor.R
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.extention.showLogoutDialog
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.FragmentDashBoardBinding
import com.rmg.production_monitor.models.remote.cumulativeDashboardSummary.CumulativeDashboardSummaryPayload
import com.rmg.production_monitor.view.activity.LoginActivity
import com.rmg.production_monitor.view.activity.MainActivity
import com.rmg.production_monitor.viewModel.CumulativeDashboardSummaryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardFragment : BaseFragment<FragmentDashBoardBinding>() {

    private val cumulativeDashboardSummaryViewModel by viewModels<CumulativeDashboardSummaryViewModel>()
    private lateinit var cumulativeDashboardSummaryPayload: CumulativeDashboardSummaryPayload
    private var flag:Boolean=false
    var lineId =  0

    override fun getViewBinding(inflater: LayoutInflater): FragmentDashBoardBinding {
        return FragmentDashBoardBinding.inflate(inflater)
    }

    override fun callInitialApi() {
        super.callInitialApi()
        lineId = cumulativeDashboardSummaryViewModel.getLineId()?.toInt() ?: 0
        networkChecker {
            cumulativeDashboardSummaryViewModel.getCumulativeDashboardSummary(lineId)
        }
    }

    override fun setupObserver() {
        super.setupObserver()
        cumulativeDashboardSummaryViewModel.cumulativeDashboardSummaryLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    hideLoader()
                    it.data?.payload?.let { payload ->
                        cumulativeDashboardSummaryPayload = payload
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

        binding.btnPause.setOnClickListener{
            if (!flag){
                flag=true
                binding.btnPause.setImageResource(R.drawable.outline_play_circle_outline_24)
                (requireActivity() as MainActivity).stopScrolling()
            }else{
                binding.btnPause.setImageResource(R.drawable.ic_pause)
                (requireActivity() as MainActivity).startAutoScroll()
                flag=false
            }

        }

        binding.btnRefresh.setOnClickListener {
            networkChecker {
                cumulativeDashboardSummaryViewModel.getCumulativeDashboardSummary(lineId)
            }
        }

        binding.btnExit.setOnClickListener {
            //showExitDialog()


            showLogoutDialog(
                requireContext(),
                onYesButtonClick = {

                    //  recreate()
                    cumulativeDashboardSummaryViewModel.clearSession()
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)

                }
            )
        }

        if (::cumulativeDashboardSummaryPayload.isInitialized) {
            "Style - ${cumulativeDashboardSummaryPayload.styleName}".also { binding.textViewStyle.text = it }
            "Color - ${cumulativeDashboardSummaryPayload.colorName}".also { binding.textViewColor.text = it }
            "Buyer - ${cumulativeDashboardSummaryPayload.buyerName}".also { binding.textViewBuyer.text = it }
            "PO - ${cumulativeDashboardSummaryPayload.poNumber}".also { binding.textViewPO.text = it }
            "Hour ${cumulativeDashboardSummaryPayload.hour}".also { binding.textViewHour.text = it }
            binding.textTargetValue.text = cumulativeDashboardSummaryPayload.target
            binding.textActualValue.text = cumulativeDashboardSummaryPayload.actual
            binding.textVarianceValue.text = cumulativeDashboardSummaryPayload.variance
            binding.textTrendValue.text = cumulativeDashboardSummaryPayload.trend
            binding.textDHUValue.text = cumulativeDashboardSummaryPayload.dHU
            binding.textOperationValue.text = cumulativeDashboardSummaryPayload.operations
            binding.textHelperValue.text = cumulativeDashboardSummaryPayload.helpers
            binding.textIronmanValue.text = cumulativeDashboardSummaryPayload.ironMan
            binding.textActualPercentValue.text = cumulativeDashboardSummaryPayload.actualEfficiency
            binding.textActualPlannedValue.text = cumulativeDashboardSummaryPayload.plannedEfficiency
            binding.textWipTotal.text = cumulativeDashboardSummaryPayload.wipTotal
        }
    }
}