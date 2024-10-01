package com.rmg.production_monitor.view.fragment


import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.FragmentPCBBinding
import com.rmg.production_monitor.models.local.entity.PCBDashBoardDetailsEntity
import com.rmg.production_monitor.models.local.viewModel.CumulativeDashBoardLocalViewModel
import com.rmg.production_monitor.models.local.viewModel.PCBDashBoardDetailsLocalViewModel
import com.rmg.production_monitor.models.remote.cumulativeDashboardDetail.ColumnName
import com.rmg.production_monitor.models.remote.cumulativeDashboardDetail.CumulativeDashboardDetailPayload
import com.rmg.production_monitor.models.remote.cumulativeDashboardDetail.HourlyDetail
import com.rmg.production_monitor.view.activity.LoginActivity
import com.rmg.production_monitor.view.activity.MainActivity
import com.rmg.production_monitor.view.adapter.PCBAdapter
import com.rmg.production_monitor.view.adapter.PCBTopColumnNameAdapter
import com.rmg.production_monitor.viewModel.CumulativeDashboardDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PCBFragment : BaseFragment<FragmentPCBBinding>() {


    private lateinit var hourlyDetailList: MutableList<HourlyDetail?>
    private lateinit var columnName: MutableList<ColumnName>
    private lateinit var pcbAdapter: PCBAdapter
    private lateinit var pcbTopColumnNameAdapter: PCBTopColumnNameAdapter
    var lineId = 0
    private lateinit var handler: Handler

    /*Local DB*/
    private lateinit var cumulativeDashboardDetailPayload: PCBDashBoardDetailsEntity
    private lateinit var pcbDashBoardDetailsLocalViewModel: PCBDashBoardDetailsLocalViewModel

    override fun getViewBinding(inflater: LayoutInflater): FragmentPCBBinding {
        return FragmentPCBBinding.inflate(inflater)
    }

    override fun callInitialApi() {
        super.callInitialApi()
        pcbDashBoardDetailsLocalViewModel=
            ViewModelProvider(this)[PCBDashBoardDetailsLocalViewModel::class.java]
        pcbDashBoardDetailsLocalViewModel.getPCBDashBoardDetailsList.observe(viewLifecycleOwner){
            if (it !=null){
                cumulativeDashboardDetailPayload=it
            }
        }


    }

    override fun setupObserver() {
        super.setupObserver()

//        cumulativeDashboardDetailViewModel.cumulativeDashboardDetailLiveData.observe(
//            viewLifecycleOwner
//        ) {
//            when (it) {
//                is NetworkResult.Success -> {
//                    hideLoader()
//                    it.data?.payload?.let { payload ->
//                        cumulativeDashboardDetailPayload = payload
//                        pcbApiCall()
//                    }
//                }
//
//                is NetworkResult.Error -> {
//                    hideLoader()
//                    it.message.toString().toast()
//
//                }
//
//                is NetworkResult.Loading -> {
//                    showLoader()
//                }
//
//                is NetworkResult.SessionOut -> {
//                    "User token expired".toast()
//                    cumulativeDashboardDetailViewModel.clearSession()
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
        if (::cumulativeDashboardDetailPayload.isInitialized) {

            binding.apply {
                textViewStyle.text = changeEndTextColor(
                    "Style - ${cumulativeDashboardDetailPayload.payload?.styleName ?: ""}",
                    6
                )
                textViewColor.text = changeEndTextColor(
                    "Color - ${cumulativeDashboardDetailPayload.payload?.colorName ?: ""}",
                    6
                )
                textViewBuyer.text = changeEndTextColor(
                    "Buyer - ${cumulativeDashboardDetailPayload.payload?.buyerName ?: ""}",
                    6
                )
                tvRunDay.text = changeEndTextColor(
                    "Run Day - ${cumulativeDashboardDetailPayload.payload?.runDay ?: ""}",
                    9
                )
                tvRunningHour.text = changeEndTextColor(
                    "Running Hour - ${cumulativeDashboardDetailPayload.payload?.runningHour ?:""}",
                    13
                )
                if (cumulativeDashboardDetailPayload.payload?.poNumber?.isNotEmpty() == true) {
                    textViewPO.text = changeEndTextColor("PO-${cumulativeDashboardDetailPayload.payload?.poNumber?:""}", 2)
                }

                if (hourlyDetailList.isNotEmpty())hourlyDetailList.clear()
                cumulativeDashboardDetailPayload.payload?.hourlyDetails?.let { hourlyDetailList.addAll(it) }
                pcbAdapter.submit(hourlyDetailList.sortedBy { it?.hour })

            }


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

            pcbAdapter = PCBAdapter(hourlyDetailList.sortedBy { it?.hour })
            binding.recyclerView.apply {
                setHasFixedSize(true)
                adapter = pcbAdapter
            }

        }

    }

    private fun changeEndTextColor(text: String, start: Int): SpannableString {
        val spannableString = SpannableString(text)
        val white = ForegroundColorSpan(Color.WHITE)
        spannableString.setSpan(white, start, text.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        return spannableString
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

    override fun onResume() {
        super.onResume()
        updateData()
        (requireActivity() as MainActivity).binding.imgBtnRefresh.setOnClickListener {
            callInitialApi()
        }
    }
    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }



}