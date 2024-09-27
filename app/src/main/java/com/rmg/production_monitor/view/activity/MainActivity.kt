package com.rmg.production_monitor.view.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.gson.Gson
import com.rmg.production_monitor.R
import com.rmg.production_monitor.core.Config
import com.rmg.production_monitor.core.adapter.DisplayFragment
import com.rmg.production_monitor.core.adapter.ScreenSlidePagerAdapter
import com.rmg.production_monitor.core.data.NetworkResult.Error
import com.rmg.production_monitor.core.data.NetworkResult.Loading
import com.rmg.production_monitor.core.data.NetworkResult.SessionOut
import com.rmg.production_monitor.core.data.NetworkResult.Success
import com.rmg.production_monitor.core.extention.log
import com.rmg.production_monitor.core.extention.showLogoutDialog
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.ActivityMainBinding
import com.rmg.production_monitor.models.local.entity.HeatMapEntity
import com.rmg.production_monitor.models.local.entity.HeatMapIssue
import com.rmg.production_monitor.models.local.entity.HeatMapOperation
import com.rmg.production_monitor.models.local.entity.HeatMapPosition
import com.rmg.production_monitor.models.local.entity.QualityPayload
import com.rmg.production_monitor.models.local.entity.StationWiseDhu
import com.rmg.production_monitor.models.local.viewModel.HeatmapLocalViewModel
import com.rmg.production_monitor.view.fragment.DashBoardFragment
import com.rmg.production_monitor.view.fragment.PCBFragment
import com.rmg.production_monitor.view.fragment.QualityFragment
import com.rmg.production_monitor.view.fragment.WipFragment
import com.rmg.production_monitor.viewModel.MainActivityViewModel
import com.rmg.production_monitor.viewModel.QualityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var currentPage = 0
    private val delayMS: Long = Config.SCREEN_ROTATION_INTERVAL
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var fragmentList: ArrayList<DisplayFragment> = ArrayList<DisplayFragment>()
    private var job: Job? = null
    private val mViewModel by viewModels<MainActivityViewModel>()
    private val qualityViewModel by viewModels<QualityViewModel>()
    var lineId =  0
    private  var loaderDialog: Dialog?=null

    /*Local DB*/
    private lateinit var heatmapLocalViewModel: HeatmapLocalViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel.saveSliderValue(false)

        binding.textlineName.text=mViewModel.getPlantLineName()
        initializeData()
        setUpAdapter()
        startCounter()


        binding.btnExit.setOnClickListener {
            showLogoutDialog(
                this,
                onYesButtonClick = {
                    mViewModel.clearSession()

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finishAfterTransition()
                }
            )
        }

        binding.btnPause.setOnClickListener {
            if (!mViewModel.getSliding()) {

                mViewModel.saveSliderValue(true)
                // autoSliderFragmentFlag = true
                binding.btnPause.setImageResource(R.drawable.outline_play_circle_outline_24)
                stopScrolling()
            } else {
                binding.btnPause.setImageResource(R.drawable.ic_pause)
                startAutoScroll()
                mViewModel.saveSliderValue(false)
                //  autoSliderFragmentFlag = false
            }

        }

        setupObserver()
    }

    private fun setupObserver() {
        qualityViewModel.heatMapLiveData.observe(this@MainActivity) {
            when (it) {
                is Success -> {
                    hideLoader()
                    it.data?.payload?.let { payload ->
//                        qualityPayload = payload
//                        initializeData()
//                        heatMapData()
                        val insertPayload=HeatMapEntity(0, QualityPayload(
                            payload.buyer,
                            payload.RunningDay,
                            payload.RununningHour,
                            payload.color,
                            payload.heatMapIssues.map { issue->
                                HeatMapIssue(
                                    issue.issueName,
                                    issue.count
                                )
                            },
                            payload.heatMapOperations.map {operations->
                               HeatMapOperation(
                                   operations.operationName,
                                   operations.count
                               )
                            },
                            payload.heatMapPositions.map {positions->
                                HeatMapPosition(
                                    positions.x,
                                    positions.y
                                )
                            },
                            payload.imageUrl,
                            payload.markingImageUrl,
                            payload.overAllDhu,
                            payload.po,
                            payload.remainingDiffective,
                            payload.stationWiseDhus.map { dhu->
                                StationWiseDhu(
                                    dhu.dHU,
                                    dhu.stationName
                                )
                            },
                            payload.style,
                            payload.totalReject

                        ))
                        heatmapLocalViewModel.insertHeatmapData(insertPayload)
                        (Gson().toJson(insertPayload)).log()

                    }
                }

                is Error -> {
                    hideLoader()
                    it.message.toString().toast()
                }

                is Loading -> {

                }

                is SessionOut -> {

                    "User token expired".toast()
                    qualityViewModel.clearSession()

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finishAfterTransition()
                }

                else -> {

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Start auto-scrolling
        //startAutoScroll()
        heatmapLocalViewModel=ViewModelProvider(this@MainActivity)[HeatmapLocalViewModel::class.java]
        /*startApiCall*/
        lineId = qualityViewModel.getLineId()?: 0
        qualityViewModel.getHeatmap(lineId)



        if (mViewModel.getSliding()) {
            mViewModel.saveSliderValue(true)
            binding.btnPause.setImageResource(R.drawable.outline_play_circle_outline_24)
            stopScrolling()
        } else {
            binding.btnPause.setImageResource(R.drawable.ic_pause)
            startAutoScroll()
            mViewModel.saveSliderValue(false)
        }
        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tvPageTitle.text = fragmentList[position].fragmentTitle
            }
        })

    }

    private fun initializeData() {
        fragmentList.add(DisplayFragment("Quality", QualityFragment()))
        fragmentList.add(DisplayFragment("PCB", PCBFragment()))
        fragmentList.add(DisplayFragment("Swing..", DashBoardFragment()))
        fragmentList.add(DisplayFragment("WIP", WipFragment()))

        handler = Handler(Looper.getMainLooper())

    }

    private fun setUpAdapter() {

        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager, lifecycle, fragmentList)
        binding.viewPager.adapter = pagerAdapter
    }

    private fun startAutoScroll() {
        runnable = Runnable {
            binding.viewPager.currentItem = currentPage % binding.viewPager.adapter!!.itemCount
            //  binding.tvPageTitle.text= fragmentList[currentPage].fragmentTitle
            currentPage++
            handler.postDelayed(runnable, delayMS)

        }

        handler.postDelayed(runnable, delayMS)
    }

    private fun startCounter() {
        job = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                updateTime()
                delay(1000) // 1000 ms = 1 second
            }
        }
    }

    private suspend fun updateTime() {
        val sdf = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
        val currentDate = sdf.format(Date())

        // Update the UI on the main thread
        withContext(Dispatchers.Main) {
            binding.textTime.text = currentDate
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        handler.removeCallbacksAndMessages(null)
    }
    private fun stopScrolling() {
        handler.removeCallbacksAndMessages(null)
    }

    private fun hideLoader() {
        if (loaderDialog == null) {
            return
        }
        loaderDialog?.hide()
        loaderDialog?.dismiss()
        loaderDialog?.cancel()
        loaderDialog=null
    }

}