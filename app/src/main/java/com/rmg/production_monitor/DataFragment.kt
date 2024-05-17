package com.rmg.production_monitor

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.rmg.production_monitor.core.adapter.DAAdapter
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.extention.showLogoutDialog
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.FragmentDataBinding
import com.rmg.production_monitor.models.remote.dasboard.WipPo
import com.rmg.production_monitor.viewModel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class DataFragment : BaseFragment<FragmentDataBinding>() {
    private val mViewModel by viewModels<DashboardViewModel>()
    private var flag:Boolean=false
  @Inject
  lateinit var daAdapter: DAAdapter
    override fun getViewBinding(inflater: LayoutInflater): FragmentDataBinding {
        return FragmentDataBinding.inflate(inflater)
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
                val lineId=  mViewModel.getLineId()
                if (lineId != null) {
                    mViewModel.getDashboardAnalytics(lineId.toInt())
                }
            }
        }

        binding.btnExit.setOnClickListener {
            //showExitDialog()


            showLogoutDialog(
                requireContext(),
                onYesButtonClick = {

                    //  recreate()
                    mViewModel.clearSession()
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)

                }
            )
        }

       // binding.appTitle.text="Qc Monitor App /br {}"
    }
    override fun callInitialApi() {
        super.callInitialApi()




        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                updateTime()
                handler.postDelayed(this, 1000)
            }
        })

        networkChecker {
          val lineId=  mViewModel.getLineId()
            if (lineId != null) {
                mViewModel.getDashboardAnalytics(lineId.toInt())
            }
        }
    }

    private fun updateTime() {
        val sdf = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
        val currentDate = sdf.format(Date())
        binding.textTime.text = currentDate.toString()
    }

    override fun setupObserver() {
        super.setupObserver()
        mViewModel.dashboardAnalyticLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    hideLoader()
                    Log.i("rakib1", "setupObserver: ${it.data?.payload}")
                    binding.apply {
                        texttimeHour.text = "${it.data?.payload?.workingHour.toString()} Hr"
                        textTotalWip.text = it.data?.payload?.totalWip.toString()
                        textlineName.text = "Line ${it.data?.payload?.lineId.toString()}"

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

    // todo rakib use base dialog fragment
    @SuppressLint("SuspiciousIndentation")
    private fun showExitDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Qc Monitor App")
        builder.setMessage("Are you sure you want to Logout?")
            .setCancelable(false)
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, id ->

                    mViewModel.clearSession()

                    requireActivity().finish()
                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        builder.create()?.show()
    }
}
