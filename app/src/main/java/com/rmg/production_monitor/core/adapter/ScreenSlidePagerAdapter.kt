package com.rmg.production_monitor.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreenSlidePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val fragments: List<DisplayFragment>) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position].fragment


}
data class DisplayFragment(
    var fragmentTitle: String,
    var fragment: Fragment


)