package com.rmg.production_monitor

import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.rmg.production_monitor.core.adapter.DashboardAnalyticsAdapter
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.FragmentDataBinding
import com.rmg.production_monitor.viewModel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date


@AndroidEntryPoint
class DataFragment : BaseFragment<FragmentDataBinding>() {
    private val mViewModel by viewModels<DashboardViewModel>()
    var dAdapter: DashboardAnalyticsAdapter? = null

    override fun getViewBinding(inflater: LayoutInflater): FragmentDataBinding {
        return FragmentDataBinding.inflate(inflater)
    }

    override fun callInitialApi() {
        super.callInitialApi()
        networkChecker {
            mViewModel.getDashboardAnalytics()
        }
    }

    override fun setupObserver() {
        super.setupObserver()
        mViewModel.dashboardAnalyticLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    hideLoader()
                    Log.i("rakib1", "setupObserver: ${it.data?.payload}")
                    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                    val currentDate = sdf.format(Date())
                    binding.apply {
                        texttimeHour.text = "${it.data?.payload?.workingHour.toString()} Hr"
                        textTotalWip.text=it.data?.payload?.totalWip.toString()
                        textlineName.text= "Line ${ it.data?.payload?.totalWip.toString() }"
                        textTime.text=currentDate.toString()
                    }
                    dAdapter =
                        it.data?.payload?.let { it1 -> DashboardAnalyticsAdapter(it1.wipPos) }
                }

                is NetworkResult.Error -> {
                    hideLoader()
                    it.message.toString().toast()
                }

                is NetworkResult.Loading -> {
                    showLoader()
                }

                is NetworkResult.SessionOut -> {
                    //  qualityViewModel.clearSession()
                    showTokenExpiredToast()
                    //(requireActivity() as MainActivity).logout()
                }

                else -> {

                }
            }
        }
    }


    override fun setUpRecycleView() {
        super.setUpRecycleView()

        binding.recyclerView.adapter = dAdapter
    }
}