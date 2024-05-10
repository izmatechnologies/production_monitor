package com.rmg.production_monitor

import android.view.LayoutInflater
import com.rmg.production_monitor.core.adapter.PCBAdapter
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.databinding.FragmentPCBBinding
import com.rmg.production_monitor.models.remote.pcb.PCBModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PCBFragment : BaseFragment<FragmentPCBBinding>() {

    private val pcbModelMutableList = mutableListOf<PCBModel>()

    override fun getViewBinding(inflater: LayoutInflater): FragmentPCBBinding {
        return FragmentPCBBinding.inflate(inflater)
    }

    override fun initializeData() {
        super.initializeData()
        repeat(10) {
            val data = PCBModel(
                "${it + 1}",
                "0 / 0",
                "0 / 0",
                "0 / 0",
                "0.0 / 0.0",
                "0%",
                "0%",
                ""
            )
            pcbModelMutableList.add(data)
        }
    }

    override fun setUpRecycleView() {
        super.setUpRecycleView()

        val pcbAdapter = PCBAdapter(pcbModelMutableList)
        binding.recyclerView.adapter = pcbAdapter
    }
}