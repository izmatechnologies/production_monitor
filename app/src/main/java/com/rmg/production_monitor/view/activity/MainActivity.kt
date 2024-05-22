package com.rmg.production_monitor.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.rmg.production_monitor.R
import com.rmg.production_monitor.core.Config
import com.rmg.production_monitor.core.adapter.DisplayFragment
import com.rmg.production_monitor.core.adapter.ScreenSlidePagerAdapter
import com.rmg.production_monitor.core.extention.showLogoutDialog
import com.rmg.production_monitor.databinding.ActivityMainBinding
import com.rmg.production_monitor.view.fragment.DashBoardFragment
import com.rmg.production_monitor.view.fragment.DataFragment
import com.rmg.production_monitor.view.fragment.PCBFragment
import com.rmg.production_monitor.view.fragment.QualityFragment
import com.rmg.production_monitor.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var currentPage = 0
    private val delayMS: Long = Config.SCREEN_ROTATION_INTERVAL
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var autoSliderFragmentFlag: Boolean = false

    private var fragmentList: ArrayList<DisplayFragment> = ArrayList<DisplayFragment>()

    private val mViewModel by viewModels<MainActivityViewModel>()
     var toolbarInterface:ToolbarInterface? = null


    fun setOnToolBarListener(toolbarInterface: ToolbarInterface?) {
        this.toolbarInterface = toolbarInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel.saveSliderValue(false)

        initializeData()
        setUpAdapter()

        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                updateTime()
                handler.postDelayed(this, delayMS)
            }
        })


        binding.btnExit.setOnClickListener {
            showLogoutDialog(
                this,
                onYesButtonClick = {
                  mViewModel.clearSession()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

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

//        binding.btnPause.setOnClickListener{
//            if (!flag){
//                flag=true
//                handler.removeCallbacks(runnable)
//            }else{
//                startAutoScroll()
//                flag=false
//            }
//
//        }
    }

    private fun updateTime() {
        val sdf = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
        val currentDate = sdf.format(Date())
        binding.textTime.text = currentDate.toString()
    }


    override fun onResume() {
        super.onResume()
        // Start auto-scrolling
        //startAutoScroll()

        if (mViewModel.getSliding()) {
            mViewModel.saveSliderValue(true)
            binding.btnPause.setImageResource(R.drawable.outline_play_circle_outline_24)
            stopScrolling()
        } else {
            binding.btnPause.setImageResource(R.drawable.ic_pause)
            startAutoScroll()
            mViewModel.saveSliderValue(false)
        }


        binding.imgBtnRefresh.setOnClickListener {
            toolbarInterface?.onRefreshButtonClick()
        }



        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tvPageTitle.text= fragmentList[position].fragmentTitle
            }
        })



    }

    fun initializeData() {

        fragmentList.add(DisplayFragment("Quality",QualityFragment()))
        fragmentList.add(DisplayFragment("PCB",PCBFragment()))
        fragmentList.add(DisplayFragment("Swing..",DashBoardFragment()))
        fragmentList.add(DisplayFragment("WIP",DataFragment()))

        handler = Handler(Looper.getMainLooper())

    }

    fun setUpAdapter() {

        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager, lifecycle, fragmentList)
        binding.viewPager.adapter = pagerAdapter
    }

    fun startAutoScroll() {
        runnable = Runnable {
            binding.viewPager.currentItem = currentPage % binding.viewPager.adapter!!.itemCount
          //  binding.tvPageTitle.text= fragmentList[currentPage].fragmentTitle
            currentPage++
            handler.postDelayed(runnable, delayMS)

       }

        handler.postDelayed(runnable, delayMS)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }


    fun stopScrolling() {
        handler.removeCallbacksAndMessages(null)
    }
}