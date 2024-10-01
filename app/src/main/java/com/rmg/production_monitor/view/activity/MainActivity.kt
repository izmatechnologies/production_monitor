package com.rmg.production_monitor.view.activity

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.gson.Gson
import com.rmg.production_monitor.R
import com.rmg.production_monitor.broadCastCallReceiver.CumulativeDashBoardSummaryCallReceiver
import com.rmg.production_monitor.core.Config
import com.rmg.production_monitor.core.adapter.DisplayFragment
import com.rmg.production_monitor.core.adapter.ScreenSlidePagerAdapter
import com.rmg.production_monitor.core.extention.log
import com.rmg.production_monitor.core.extention.showLogoutDialog
import com.rmg.production_monitor.core.managers.preference.AppPreferenceImpl
import com.rmg.production_monitor.databinding.ActivityMainBinding
import com.rmg.production_monitor.models.local.entity.CumulativeDashBoardEntity
import com.rmg.production_monitor.models.local.entity.HeatMapEntity
import com.rmg.production_monitor.models.local.viewModel.CumulativeDashBoardLocalViewModel
import com.rmg.production_monitor.models.local.viewModel.HeatmapLocalViewModel
import com.rmg.production_monitor.broadCastCallReceiver.HeatmapCallReceiver
import com.rmg.production_monitor.broadCastCallReceiver.PCBDashboardDetailsApiCallReceiver
import com.rmg.production_monitor.broadCastCallReceiver.WIPAnalyticsApiCallReceiver
import com.rmg.production_monitor.models.local.entity.PCBDashBoardDetailsEntity
import com.rmg.production_monitor.models.local.entity.WIPAnalyticsEntity
import com.rmg.production_monitor.models.local.viewModel.PCBDashBoardDetailsLocalViewModel
import com.rmg.production_monitor.models.local.viewModel.WIPAnalyticsLocalViewModel
import com.rmg.production_monitor.view.fragment.DashBoardFragment
import com.rmg.production_monitor.view.fragment.PCBFragment
import com.rmg.production_monitor.view.fragment.QualityFragment
import com.rmg.production_monitor.view.fragment.WipFragment
import com.rmg.production_monitor.viewModel.CumulativeDashboardDetailViewModel
import com.rmg.production_monitor.viewModel.CumulativeDashboardSummaryViewModel
import com.rmg.production_monitor.viewModel.DashboardViewModel
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
    private val cumulativeDashboardSummaryViewModel by viewModels<CumulativeDashboardSummaryViewModel>()
    private val wipViewModel by viewModels<DashboardViewModel>()
    private val cumulativeDashboardDetailViewModel by viewModels<CumulativeDashboardDetailViewModel>()
    var lineId = 0
    private var loaderDialog: Dialog? = null
    private lateinit var prefs: AppPreferenceImpl

    /*Local DB*/
    private lateinit var heatmapLocalViewModel: HeatmapLocalViewModel
    private lateinit var cumulativeDashBoardLocalViewModel: CumulativeDashBoardLocalViewModel
    private lateinit var wipAnalyticsLocalViewModel: WIPAnalyticsLocalViewModel
    private lateinit var pcbDashBoardDetailsLocalViewModel: PCBDashBoardDetailsLocalViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel.saveSliderValue(false)

        prefs = AppPreferenceImpl(this)
        binding.textlineName.text = mViewModel.getPlantLineName()
        initializeData()
        setUpAdapter()
        startCounter()



        lineId = qualityViewModel.getLineId() ?: 0
        /*Heat map api*/
        qualityViewModel.getHeatmap(lineId)
        scheduleHeatMapApiCall(this, lineId)

        /*cumulativeDashboard Summary*/
        cumulativeDashboardSummaryViewModel.getCumulativeDashboardSummary(lineId)
        scheduleCumulativeDashBoardSummaryApiCall(this,lineId)

        /*WIP*/
        mViewModel.getLineId()?.let { wipViewModel.getDashboardAnalytics(it) }
        scheduleWIPApiCall(this,lineId)

        /*DashBoard Details*/
        cumulativeDashboardDetailViewModel.getCumulativeDashboardDetail(lineId)
        schedulePCBDashboardDetailsApiCall(this,lineId)




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

        /*Heat map*/
        qualityViewModel.heatMapLiveData.observe(this@MainActivity) {
            it?.payload?.let { payload ->
                val insertPayload = HeatMapEntity(0, payload)

                heatmapLocalViewModel.insertHeatmapData(insertPayload)

                (Gson().toJson(insertPayload)).log()

            }
//            when (it) {
//                is Success -> {
//                    hideLoader()
//
//                }
//
//                is Error -> {
//                    hideLoader()
//                    it.message.toString().toast()
//                }
//
//                is Loading -> {
//
//                }
//
//                is SessionOut -> {
//
//                    "User token expired".toast()
//                    qualityViewModel.clearSession()
//
//                    val intent = Intent(this, LoginActivity::class.java)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                    startActivity(intent)
//                    finishAfterTransition()
//                }
//
//                else -> {
//
//                }
//            }
        }

        /*Cumulative DashBoard*/
        cumulativeDashboardSummaryViewModel.cumulativeDashboardSummaryLiveData.observe(this@MainActivity){dashBoard->
            lifecycleScope.launch {
                if (dashBoard.success){
                    val data=CumulativeDashBoardEntity(0,dashBoard.payload)
                    cumulativeDashBoardLocalViewModel.insertCumulativeDashBoardData(data)
                }
            }
        }

        /*WIP*/
        wipViewModel.dashboardAnalyticLiveData.observe(this@MainActivity){wip->
            lifecycleScope.launch {
                if (wip.success){
                   val data=WIPAnalyticsEntity(0,wip.payload)
                    wipAnalyticsLocalViewModel.insertWipAnalyticsData(data)
                }
            }
        }

        /*DashBoard Details*/
        cumulativeDashboardDetailViewModel.cumulativeDashboardDetailLiveData.observe(this@MainActivity){details->
            lifecycleScope.launch {
                if(details.success == true){
                    val data=PCBDashBoardDetailsEntity(0,details.payload)
                    pcbDashBoardDetailsLocalViewModel.insertCumulativeDashBoardData(data)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Start auto-scrolling
        //startAutoScroll()
        heatmapLocalViewModel = ViewModelProvider(this@MainActivity)[HeatmapLocalViewModel::class.java]
        cumulativeDashBoardLocalViewModel = ViewModelProvider(this@MainActivity)[CumulativeDashBoardLocalViewModel::class.java]
        wipAnalyticsLocalViewModel = ViewModelProvider(this@MainActivity)[WIPAnalyticsLocalViewModel::class.java]
        pcbDashBoardDetailsLocalViewModel = ViewModelProvider(this@MainActivity)[PCBDashBoardDetailsLocalViewModel::class.java]

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
        cancelApiCalls()
    }

    private fun cancelApiCalls() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // Cancel API 1
        val api1Intent = Intent(this, HeatmapCallReceiver::class.java)
        val api1PendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            api1Intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(api1PendingIntent)
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
        loaderDialog = null
    }


    private fun scheduleHeatMapApiCall(context: Context, lineId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, HeatmapCallReceiver::class.java).apply {
            putExtra("LINE_ID", lineId.toString())
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set repeating alarm for API every 3 minutes
        alarmManager.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(), // First trigger
            1 * 60 * 1000, // Repeat every 3 minutes
            pendingIntent
        )
    }

    private fun scheduleCumulativeDashBoardSummaryApiCall(context: MainActivity, lineId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, CumulativeDashBoardSummaryCallReceiver::class.java).apply {
            putExtra("LINE_ID", lineId.toString())
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set repeating alarm for API every 3 minutes
        alarmManager.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(), // First trigger
            2 * 60 * 1000, // Repeat every 3 minutes
            pendingIntent
        )
    }

    private fun scheduleWIPApiCall(context: MainActivity, lineId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, WIPAnalyticsApiCallReceiver::class.java).apply {
            putExtra("LINE_ID", lineId.toString())
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            2,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set repeating alarm for API every 3 minutes
        alarmManager.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(), // First trigger
            2 * 60 * 1000, // Repeat every 3 minutes
            pendingIntent
        )
    }

    private fun schedulePCBDashboardDetailsApiCall(context: MainActivity, lineId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, PCBDashboardDetailsApiCallReceiver::class.java).apply {
            putExtra("LINE_ID", lineId.toString())
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            3,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set repeating alarm for API every 3 minutes
        alarmManager.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(), // First trigger
            2 * 60 * 1000, // Repeat every 3 minutes
            pendingIntent
        )
    }

}