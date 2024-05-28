package com.rmg.production_monitor.view.fragment

import android.R
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.FragmentDashBoardBinding
import com.rmg.production_monitor.models.remote.cumulativeDashboardSummary.CumulativeDashboardSummaryPayload
import com.rmg.production_monitor.view.activity.MainActivity
import com.rmg.production_monitor.viewModel.CumulativeDashboardSummaryViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashBoardFragment : BaseFragment<FragmentDashBoardBinding>() {

    private val cumulativeDashboardSummaryViewModel by viewModels<CumulativeDashboardSummaryViewModel>()
    private lateinit var cumulativeDashboardSummaryPayload: CumulativeDashboardSummaryPayload
    private var flag: Boolean = false
    var lineId = 0
    private lateinit var spannableStringBuilder: SpannableStringBuilder
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
        cumulativeDashboardSummaryViewModel.cumulativeDashboardSummaryLiveData.observe(
            viewLifecycleOwner
        ) {
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

                else -> {

                }
            }
        }
    }

    override fun initializeData() {
        super.initializeData()
        if (::cumulativeDashboardSummaryPayload.isInitialized) {




            binding.apply {
                textViewStyle.text = changeEndTextColor("Style - ${cumulativeDashboardSummaryPayload.styleName}", 6)
                textViewColor.text = changeEndTextColor("Color - ${cumulativeDashboardSummaryPayload.colorName}", 6)
                textViewBuyer.text = changeEndTextColor("Buyer - ${cumulativeDashboardSummaryPayload.buyerName}", 6)
                tvRunDay.text = changeEndTextColor("Run Day - ${cumulativeDashboardSummaryPayload.runDay}", 9)
                tvRuningHour.text = changeEndTextColor("Running Hour - ${cumulativeDashboardSummaryPayload.runningHour}", 13)
                if (cumulativeDashboardSummaryPayload.poNumber?.isNotEmpty() == true) {
                    textViewPO.text = changeEndTextColor(cumulativeDashboardSummaryPayload.poNumber ?: "", 2)
                }


                textTargetValue.text = cumulativeDashboardSummaryPayload.target
                textActualValue.text = cumulativeDashboardSummaryPayload.actual
                textVarianceValue.text = cumulativeDashboardSummaryPayload.variance
                textTrendValue.text = cumulativeDashboardSummaryPayload.trend
//                textDHUValue.text=changeTextSize("${cumulativeDashboardSummaryPayload.dHU}%",5.5f,"DHU",1.0f,)
                textDHUValue.text="${cumulativeDashboardSummaryPayload.dHU}%"

                textHelperValue.text = cumulativeDashboardSummaryPayload.helpers
                textActualPercentValue.text = "${cumulativeDashboardSummaryPayload.actualEfficiency} %"
                progressBar.progress = cumulativeDashboardSummaryPayload.actualEfficiency?.toInt() ?: 0
                textActualPlannedValue.text = changeTextSize("Planned",.5f,"${cumulativeDashboardSummaryPayload.plannedEfficiency}%",4.0f)
                textWipTotal.text = cumulativeDashboardSummaryPayload.wipTotal
                textOperationValue.text = cumulativeDashboardSummaryPayload.operators
                textIronmanValue.text = cumulativeDashboardSummaryPayload.ironMen
            }

        }


    }


    private fun changeTextSize(word1:String,word1Size:Float,word2:String,word2Size:Float): SpannableStringBuilder {
        spannableStringBuilder=SpannableStringBuilder()
        spannableStringBuilder.append(word1, RelativeSizeSpan(word1Size), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder.append(" ")
        spannableStringBuilder.append(word2, RelativeSizeSpan(word2Size), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableStringBuilder
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
            lineId = cumulativeDashboardSummaryViewModel.getLineId()?.toInt() ?: 0
            networkChecker {
                cumulativeDashboardSummaryViewModel.getCumulativeDashboardSummary(lineId)
            }
        }
    }


}