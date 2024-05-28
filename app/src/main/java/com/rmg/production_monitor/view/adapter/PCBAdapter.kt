package com.rmg.production_monitor.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.rmg.production_monitor.databinding.RowPCBBinding
import com.rmg.production_monitor.databinding.RowPCBMainBinding
import com.rmg.production_monitor.models.remote.cumulativeDashboardDetail.HourlyDetail

class PCBAdapter(
    private val context: Context,
    private var hourlyDetailList: List<HourlyDetail?>
) : RecyclerView.Adapter<PCBAdapter.PCBViewHolder>() {

    fun submit(hourlyDetailList: List<HourlyDetail?>){
        this.hourlyDetailList=hourlyDetailList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PCBViewHolder {
        return PCBViewHolder(RowPCBMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return hourlyDetailList.size
    }

    override fun onBindViewHolder(holder: PCBViewHolder, position: Int) {
        val item = hourlyDetailList[position]
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class PCBViewHolder(
        private val binding: RowPCBMainBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HourlyDetail) {
            binding.apply {
                textHour.text = item.hour.toString()

                val pcbChildAdapter= item.columnValues?.let { PCBChildAdapter(it) }

                recyclerView.apply {
                    adapter=pcbChildAdapter
                }

            }
        }
    }
}