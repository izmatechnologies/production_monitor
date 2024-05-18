package com.rmg.production_monitor.view.fragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.extention.log
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.FragmentDataBinding
import com.rmg.production_monitor.models.remote.dasboard.WipPo
import com.rmg.production_monitor.view.activity.MainActivity
import com.rmg.production_monitor.view.activity.ToolbarInterface
import com.rmg.production_monitor.view.adapter.DAAdapter
import com.rmg.production_monitor.viewModel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DataFragment : BaseFragment<FragmentDataBinding>(), ToolbarInterface {
    private val mViewModel by viewModels<DashboardViewModel>()
    private var flag: Boolean = false

    @Inject
    lateinit var daAdapter: DAAdapter
    override fun getViewBinding(inflater: LayoutInflater): FragmentDataBinding {
        return FragmentDataBinding.inflate(inflater)
    }

    override fun initializeData() {
        super.initializeData()


    }

    override fun callInitialApi() {
        super.callInitialApi()

        networkChecker {
          val lineId=  mViewModel.getLineId()
            if (lineId != null) {
                mViewModel.getDashboardAnalytics(lineId.toInt())
            }
        }
    }



    override fun setupObserver() {
        super.setupObserver()
        mViewModel.dashboardAnalyticLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    hideLoader()
                    Log.i("rakib1", "setupObserver: ${it.data?.payload}")
                    binding.apply {
                   //     texttimeHour.text = "${it.data?.payload?.workingHour.toString()} Hr"
                        textTotalWip.text = it.data?.payload?.totalWip.toString()
                   //     textlineName.text = "Line ${it.data?.payload?.lineId.toString()}"

                    }
                    setUpRecyclerView(it.data?.payload?.wipPos)
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


    private fun setUpRecyclerView(payload: List<WipPo?>?) {


        payload?.apply {
            daAdapter.differ.submitList(toList())

        }

        binding.recyclerView.apply {
            adapter = daAdapter
        }
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
