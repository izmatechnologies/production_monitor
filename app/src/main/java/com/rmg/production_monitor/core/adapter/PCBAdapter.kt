package com.rmg.production_monitor.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rmg.production_monitor.databinding.RowPCBBinding
import com.rmg.production_monitor.models.remote.pcb.PCBModel

class PCBAdapter(
    private val pcbModelList: List<PCBModel>
) : RecyclerView.Adapter<PCBAdapter.PCBViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PCBViewHolder {
        return PCBViewHolder(RowPCBBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return pcbModelList.size
    }

    override fun onBindViewHolder(holder: PCBViewHolder, position: Int) {
        val item = pcbModelList[position]
        holder.bind(item)
    }

    inner class PCBViewHolder(
        private val binding: RowPCBBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PCBModel) {
            binding.textView1.text = item.string1
            binding.textView2.text = item.string2
            binding.textView3.text = item.string3
            binding.textView4.text = item.string4
            binding.textView5.text = item.string5
            binding.textView6.text = item.string6
            binding.textView7.text = item.string7
            binding.textView8.text = item.string8
        }
    }
}