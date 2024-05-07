package com.rmg.production_monitor

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rmg.production_monitor.core.adapter.ScreenSlidePagerAdapter
import com.rmg.production_monitor.core.base.BaseActivity
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    lateinit var binding: ActivityMainBinding
    private var currentPage = 0
    private val delayMS: Long = 5000 // 5 seconds delay
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var flag:Boolean=false

    private var fragmentList: List<Fragment> = ArrayList<Fragment>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeData()
        setUpAdapter()

        binding.btnPause.setOnClickListener{
            if (!flag){
                flag=true
                handler.removeCallbacks(runnable)
            }else{
                startAutoScroll()
                flag=false
            }

        }
    }


    override fun onResume() {
        super.onResume()
        // Start auto-scrolling
        startAutoScroll()
    }

     fun initializeData() {

        fragmentList = listOf(QualityFragment(), PCBFragment(),DashBoardFragment(),DataFragment())
        handler = Handler(Looper.getMainLooper())
    }

     fun setUpAdapter() {

        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager, lifecycle, fragmentList)
        binding.viewPager.adapter = pagerAdapter
    }

    private fun startAutoScroll() {
        runnable = Runnable {
            binding.viewPager.currentItem = currentPage % binding.viewPager.adapter!!.itemCount
            currentPage++
            handler.postDelayed(runnable, delayMS)
        }

        handler.postDelayed(runnable, delayMS)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}