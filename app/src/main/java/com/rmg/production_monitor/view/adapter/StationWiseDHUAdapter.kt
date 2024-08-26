package com.rmg.production_monitor.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rmg.production_monitor.R
import com.rmg.production_monitor.databinding.StationWisezDhuLayoutBinding
import com.rmg.production_monitor.models.remote.quality.DhuValueList

class StationWiseDHUAdapter(
    private val context: Context,
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
                if ((item.value?.replace("%","")?.toDouble()?:0.0) > 3.00){
                    tvValue.setTextColor(context.getColorStateList(R.color.red))
                }else{
                    tvValue.setTextColor(context.getColorStateList(R.color.green_light))
                }
            }
        }
    }
}