package com.rmg.production_monitor.view.fragment

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.extention.log
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.FragmentWipBinding
import com.rmg.production_monitor.models.local.entity.WIPAnalyticsEntity
import com.rmg.production_monitor.models.local.viewModel.WIPAnalyticsLocalViewModel

import com.rmg.production_monitor.models.remote.dasboard.WipPo
import com.rmg.production_monitor.view.activity.LoginActivity
import com.rmg.production_monitor.view.activity.MainActivity
import com.rmg.production_monitor.view.adapter.WIPAdapter
import com.rmg.production_monitor.viewModel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WipFragment : BaseFragment<FragmentWipBinding>() {
    private var updateJob: Job? = null
  //  private lateinit var handler: Handler
    @Inject
    lateinit var WIPAdapter: WIPAdapter

    /*Local DB*/
    private lateinit var wipAnalyticsEntity: WIPAnalyticsEntity
    private lateinit var wipAnalyticsLocalViewModel: WIPAnalyticsLocalViewModel
    override fun getViewBinding(inflater: LayoutInflater): FragmentWipBinding {
        return FragmentWipBinding.inflate(inflater)
    }

    override fun initializeData() {
        super.initializeData()
        (requireActivity()as MainActivity).binding.imgBtnRefresh.setOnClickListener {


        }

    }

    override fun callInitialApi() {
        super.callInitialApi()
        wipAnalyticsLocalViewModel=ViewModelProvider(this)[WIPAnalyticsLocalViewModel::class.java]
        wipAnalyticsLocalViewModel.getWIPListData.observe(this){
            if (it !=null){
                wipAnalyticsEntity=it

                setUpRecyclerView(wipAnalyticsEntity.payload?.wipPos)

                (Gson().toJson(wipAnalyticsEntity)).log()

            }
        }
    }



    override fun setupObserver() {
        super.setupObserver()
//        mViewModel.dashboardAnalyticLiveData.observe(viewLifecycleOwner) {
//            when (it) {
//                is NetworkResult.Success -> {
//                    hideLoader()
//                    "success".log("192")
//                    Log.i("rakib1", "setupObserver: ${it.data?.payload}")
//                    binding.apply {
//                        textTotalWip.text = it.data?.payload?.totalWip.toString()
//                    }
//                    setUpRecyclerView(it.data?.payload?.wipPos)
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
//                     // qualityViewModel.clearSession()
//                    "User token expired".toast()
//                    mViewModel.clearSession()
//
//                    val intent = Intent(requireActivity(), LoginActivity::class.java)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                    startActivity(intent)
//                    requireActivity().finishAfterTransition()
//
//
//                 //   showTokenExpiredToast()
//                 //   (requireActivity() as MainActivity).logout()
//                }
//
//                else -> {
//
//                }
//            }
//        }
    }


    private fun setUpRecyclerView(payload: List<WipPo?>?) {
        payload?.apply {
            WIPAdapter.differ.submitList(toList())
        }

        binding.recyclerView.apply {
            adapter = WIPAdapter
        }
}

//    private fun updateData() {
//        handler = Handler(Looper.getMainLooper())
//        handler.post(object : Runnable {
//            override fun run() {
//                callInitialApi()
//                handler.postDelayed(this, 10000)
//            }
//        })
//    }

    private fun updateData() {
        updateJob?.cancel()
        // Start a new coroutine in the main thread (UI)
        updateJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                // Call your initial API function
                callInitialApi()
                // Suspend for 10 seconds (non-blocking)
                delay(10000)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateData()
        (requireActivity() as MainActivity).binding.imgBtnRefresh.setOnClickListener {
            callInitialApi()
        }
    }

//    override fun onPause() {
//        super.onPause()
//       // handler.removeCallbacksAndMessages(null)
//    }

    override fun onDestroy() {
        super.onDestroy()
        updateJob?.cancel()
    }

}