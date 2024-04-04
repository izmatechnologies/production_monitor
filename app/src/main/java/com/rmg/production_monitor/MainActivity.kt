package com.rmg.production_monitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private var currentPage = 0
    private val delayMS: Long = 5000 // 5 seconds delay
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.pager)

        val fragmentList = listOf(QualityFragment(), PCBFragment())

        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager, lifecycle, fragmentList)
        viewPager.adapter = pagerAdapter

        // Start auto-scrolling
        startAutoScroll()
    }

    private fun startAutoScroll() {
        runnable = Runnable {
            viewPager.currentItem = currentPage % viewPager.adapter!!.itemCount
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