package com.rmg.production_monitor.view.fragment


import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.FragmentPCBBinding
import com.rmg.production_monitor.models.remote.cumulativeDashboardDetail.ColumnName
import com.rmg.production_monitor.models.remote.cumulativeDashboardDetail.CumulativeDashboardDetailPayload
import com.rmg.production_monitor.models.remote.cumulativeDashboardDetail.HourlyDetail
import com.rmg.production_monitor.view.activity.MainActivity
import com.rmg.production_monitor.view.adapter.PCBAdapter
import com.rmg.production_monitor.view.adapter.PCBTopColumnNameAdapter
import com.rmg.production_monitor.viewModel.CumulativeDashboardDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PCBFragment : BaseFragment<FragmentPCBBinding>() {

    private val cumulativeDashboardDetailViewModel by viewModels<CumulativeDashboardDetailViewModel>()
    private lateinit var cumulativeDashboardDetailPayload: CumulativeDashboardDetailPayload
    private lateinit var hourlyDetailList: MutableList<HourlyDetail?>
    private lateinit var columnName: MutableList<ColumnName>
    private lateinit var pcbAdapter: PCBAdapter
    private lateinit var pcbTopColumnNameAdapter: PCBTopColumnNameAdapter
    private var flag: Boolean = false
    var lineId = 0

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

        cumulativeDashboardDetailViewModel.cumulativeDashboardDetailLiveData.observe(
            viewLifecycleOwner
        ) {
            when (it) {
                is NetworkResult.Success -> {
                    hideLoader()
                    it.data?.payload?.let { payload ->
                        cumulativeDashboardDetailPayload = payload
                        pcbApiCall()
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

                else -> {

                }
            }
        }
    }

    private fun pcbApiCall() {
        if (::cumulativeDashboardDetailPayload.isInitialized) {

            binding.apply {
                textViewStyle.text = changeEndTextColor(
                    "Style - ${cumulativeDashboardDetailPayload.styleName ?: ""}",
                    6
                )
                textViewColor.text = changeEndTextColor(
                    "Color - ${cumulativeDashboardDetailPayload.colorName ?: ""}",
                    6
                )
                textViewBuyer.text = changeEndTextColor(
                    "Buyer - ${cumulativeDashboardDetailPayload.buyerName ?: ""}",
                    6
                )
                tvRunDay.text = changeEndTextColor(
                    "Run Day - ${cumulativeDashboardDetailPayload.runDay ?: 0}",
                    9
                )
                tvRunningHour.text = changeEndTextColor(
                    "Running Hour - ${cumulativeDashboardDetailPayload.runningHour ?: 0}",
                    13
                )
                if (cumulativeDashboardDetailPayload.poNumber?.isNotEmpty() == true) {
                    textViewPO.text =
                        changeEndTextColor(cumulativeDashboardDetailPayload.poNumber ?: "", 2)
                }

                if (hourlyDetailList.isNotEmpty())hourlyDetailList.clear()
                cumulativeDashboardDetailPayload.hourlyDetails?.let { hourlyDetailList.addAll(it) }
                pcbAdapter.submit(hourlyDetailList)

            }


        }

    }

    override fun initializeData() {
        super.initializeData()
        hourlyDetailList = mutableListOf()
        columnName = mutableListOf()
        columnName.addAll(
            mutableListOf(
                ColumnName("Hour"),
                ColumnName("HOURLY [PCS] ACTUAL / PLAN"),
                ColumnName("CUM. [PCS] ACTUAL / PLAN"),
                ColumnName("VARIANCE [PCS] HOURLY / CUM."),
                ColumnName("CUM.SAH [PCS] ACTUAL / CUM."),
                ColumnName("DHU"),
                ColumnName("ACTUAL EFF%"),
                ColumnName("EFF.VARIANCE%"),
            )
        )
        pcbTopColumnNameAdapter=PCBTopColumnNameAdapter(columnName)
        binding.recyclerViewTop.adapter=pcbTopColumnNameAdapter

        pcbAdapter = PCBAdapter(requireContext(), hourlyDetailList)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = pcbAdapter
        }
    }

    private fun changeEndTextColor(text: String, start: Int): SpannableString {
        val spannableString = SpannableString(text)
        val white = ForegroundColorSpan(Color.WHITE)
        spannableString.setSpan(white, start, text.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        return spannableString
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