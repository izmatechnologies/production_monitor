package com.rmg.production_monitor.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rmg.production_monitor.R
import com.rmg.production_monitor.databinding.RowPCBBinding
import com.rmg.production_monitor.models.remote.cumulativeDashboardDetail.ColumnValue

class PCBChildAdapter(
    private var hourlyDetailList: List<ColumnValue?>
) : RecyclerView.Adapter<PCBChildAdapter.PCBViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PCBViewHolder {
        return PCBViewHolder(RowPCBBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
        private val binding: RowPCBBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ColumnValue) {
            binding.apply {
                if (item.columnType=="Multi"){
                    textPcb.text="${item.actual?.toInt()?:0}/${item.planned?.toInt()?:0}"
                }else{
                    textPcb.text="${item.singleValue?:0.0}${item.symbol}"
                    if (item.isUp == true){
                        textPcb.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_arrow_drop_up_24, 0)
                    }else{
                        textPcb.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_arrow_drop_down_24, 0)
                    }
                }
            }
        }
    }
}