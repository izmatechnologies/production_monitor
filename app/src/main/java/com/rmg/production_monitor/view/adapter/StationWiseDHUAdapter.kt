package com.rmg.production_monitor.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rmg.production_monitor.databinding.StationWisezDhuLayoutBinding
import com.rmg.production_monitor.models.remote.quality.DhuValueList

class StationWiseDHUAdapter(
    private var dhuList: List<DhuValueList>
) : RecyclerView.Adapter<StationWiseDHUAdapter.StationWiseDHUHolder>() {

     fun submit(dhuList: List<DhuValueList>){
        this.dhuList=dhuList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationWiseDHUHolder {
        return StationWiseDHUHolder(
            StationWisezDhuLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dhuList.size
    }

    override fun onBindViewHolder(holder: StationWiseDHUHolder, position: Int) {
        val item = dhuList[position]
        holder.bind(item)
    }

    inner class StationWiseDHUHolder(
        private val binding: StationWisezDhuLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DhuValueList) {
            binding.apply {
                tvName.text=item.name
                tvValue.text=item.value
            }
        }
    }
}