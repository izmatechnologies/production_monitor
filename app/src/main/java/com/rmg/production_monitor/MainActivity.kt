package com.rmg.production_monitor

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.rmg.production_monitor.core.adapter.ScreenSlidePagerAdapter
import com.rmg.production_monitor.core.base.BaseActivity
import com.rmg.production_monitor.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {


    private var currentPage = 0
    private val delayMS: Long = 5000 // 5 seconds delay
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private var fragmentList: List<Fragment> = ArrayList<Fragment>()
    override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onResume() {
        super.onResume()
        // Start auto-scrolling
        startAutoScroll()
    }

    override fun initializeData() {
        super.initializeData()
        fragmentList = listOf(QualityFragment(), PCBFragment(),DashBoardFragment(),DataFragment())
        handler = Handler(Looper.getMainLooper())
    }

    override fun setUpAdapter() {
        super.setUpAdapter()
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