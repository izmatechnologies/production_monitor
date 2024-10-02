package com.rmg.production_monitor.view.fragment

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.extention.log
import com.rmg.production_monitor.databinding.FragmentDashBoardBinding
import com.rmg.production_monitor.models.local.entity.CumulativeDashBoardEntity
import com.rmg.production_monitor.models.local.viewModel.CumulativeDashBoardLocalViewModel
import com.rmg.production_monitor.models.remote.cumulativeDashboardSummary.CumulativeDashboardSummaryPayload
import com.rmg.production_monitor.view.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashBoardFragment : BaseFragment<FragmentDashBoardBinding>() {


//    private lateinit var cumulativeDashboardSummaryPayload: CumulativeDashboardSummaryPayload
    var lineId = 0
    private lateinit var handler: Handler
    private lateinit var spannableStringBuilder: SpannableStringBuilder
    /*Local DB*/
//    private lateinit var cumulativeDashboardSummaryPayload: CumulativeDashBoardEntity
    private lateinit var cumulativeDashBoardLocalViewModel: CumulativeDashBoardLocalViewModel
    override fun getViewBinding(inflater: LayoutInflater): FragmentDashBoardBinding {
        return FragmentDashBoardBinding.inflate(inflater)
    }

    override fun callInitialApi() {
        super.callInitialApi()
        cumulativeDashBoardLocalViewModel=ViewModelProvider(this)[CumulativeDashBoardLocalViewModel::class.java]
        cumulativeDashBoardLocalViewModel.getCumulativeDashBoardList.observe(viewLifecycleOwner){
            if (it !=null){
//                cumulativeDashboardSummaryPayload=it
                (Gson().toJson(it)).log()

                setData(it.payload)
            }
        }




    }

    private fun setData(payload: CumulativeDashboardSummaryPayload?) {
        binding.apply {
            textViewStyle.text = changeEndTextColor("Style - ${payload?.styleName}", 6)
            textViewColor.text = changeEndTextColor("Color - ${payload?.colorName}", 6)
            textViewBuyer.text = changeEndTextColor("Buyer - ${payload?.buyerName}", 6)
            tvRunDay.text = changeEndTextColor("Run Day - ${payload?.runDay}", 9)
            tvRuningHour.text = changeEndTextColor("Running Hour - ${payload?.runningHour}", 13)
            if (payload?.poNumber?.isNotEmpty() == true) {
                textViewPO.text = changeEndTextColor("PO-${payload?.poNumber?:""}", 2)
            }


            textTargetValue.text = payload?.target
            textActualValue.text = payload?.actual
            textVarianceValue.text = payload?.variance
            textTrendValue.text = payload?.trend
            textDHUValue.text="${payload?.dHU}%"

            textHelperValue.text = payload?.helpers
            textActualPercentValue.text = "${payload?.actualEfficiency} %"
            progressBar.progress = payload?.actualEfficiency?.toInt() ?: 0
            textActualPlannedValue.text = changeTextSize("Planned",.5f,"${payload?.plannedEfficiency}%",4.0f)
            textWipTotal.text = payload?.wipTotal
            textOperationValue.text = payload?.operators
            textIronmanValue.text = payload?.ironMen
            textDayTarget.text = payload?.dayTarget
        }
    }

    override fun setupObserver() {
        super.setupObserver()
//        cumulativeDashboardSummaryViewModel.cumulativeDashboardSummaryLiveData.observe(
//            viewLifecycleOwner
//        ) {
//            when (it) {
//                is NetworkResult.Success -> {
//                    hideLoader()
//                    it.data?.payload?.let { payload ->
//                        cumulativeDashboardSummaryPayload = payload
//                        initializeData()
//                    }
//                }
//
//                is NetworkResult.Error -> {
//                    hideLoader()
//                    it.message.toString().toast()
//                }
//
//                is NetworkResult.Loading -> {
//                    showLoader()
//                }
//
//                is NetworkResult.SessionOut -> {
//
//                    "User token expired".toast()
//                    cumulativeDashboardSummaryViewModel.clearSelection()
//
//                    val intent = Intent(requireActivity(), LoginActivity::class.java)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                    startActivity(intent)
//                    requireActivity().finishAfterTransition()
//
//                    // showTokenExpiredToast()
//                }
//
//                else -> {
//
//                }
//            }
//        }
    }

    override fun initializeData() {
        super.initializeData()
//        if (::cumulativeDashboardSummaryPayload.isInitialized) {
//
//        }


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
        updateData()
        (requireActivity() as MainActivity).binding.imgBtnRefresh.setOnClickListener {
            callInitialApi()
        }
    }

    private fun updateData() {
        handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                callInitialApi()
                handler.postDelayed(this, 10000)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }


}