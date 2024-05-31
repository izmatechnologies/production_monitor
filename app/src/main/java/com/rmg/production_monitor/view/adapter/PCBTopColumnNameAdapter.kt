package com.rmg.production_monitor.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rmg.production_monitor.databinding.RowPCBTopColumnNameBinding
import com.rmg.production_monitor.models.remote.cumulativeDashboardDetail.ColumnName

class PCBTopColumnNameAdapter(
    private var hourlyDetailList: List<ColumnName>
) : RecyclerView.Adapter<PCBTopColumnNameAdapter.PCBViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PCBViewHolder {
        return PCBViewHolder(RowPCBTopColumnNameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return hourlyDetailList.size
    }

    override fun onBindViewHolder(holder: PCBViewHolder, position: Int) {
        val item = hourlyDetailList[position]
        holder.bind(item)
    }

    inner class PCBViewHolder(
        private val binding: RowPCBTopColumnNameBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ColumnName) {
            binding.textPcb.text=item.name
        }
    }
}