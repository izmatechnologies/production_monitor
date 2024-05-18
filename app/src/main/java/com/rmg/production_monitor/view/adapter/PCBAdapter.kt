package com.rmg.production_monitor.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rmg.production_monitor.databinding.RowPCBBinding
import com.rmg.production_monitor.models.remote.CumulativeDashboardDetail.HourlyDetail

class PCBAdapter(
    private val hourlyDetailList: List<HourlyDetail>
) : RecyclerView.Adapter<PCBAdapter.PCBViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PCBViewHolder {
        return PCBViewHolder(RowPCBBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return hourlyDetailList.size
    }

    override fun onBindViewHolder(holder: PCBViewHolder, position: Int) {
        val item = hourlyDetailList[position]
        holder.bind(item)
    }

    inner class PCBViewHolder(
        private val binding: RowPCBBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HourlyDetail) {
            binding.textView1.text = item.hour.toString()
            binding.textView2.text = item.hourlyPcsActualPlan
            binding.textView3.text = item.cumulativePcsActualPlan
            binding.textView4.text = item.variancePcsHourlyCum
//            binding.textView5.text = item.string5
//            binding.textView6.text = item.string6
//            binding.textView7.text = item.string7
//            binding.textView8.text = item.string8
        }
    }
}