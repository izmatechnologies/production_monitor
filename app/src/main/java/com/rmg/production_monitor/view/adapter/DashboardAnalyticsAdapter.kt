package com.rmg.production_monitor.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rmg.production_monitor.databinding.RowMonitorDataItemBinding
import com.rmg.production_monitor.models.remote.dasboard.WipPo

class DashboardAnalyticsAdapter(
    private val dashboardAnalyticsList: List<WipPo>
) : RecyclerView.Adapter<DashboardAnalyticsAdapter.DashboardAnalyticsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardAnalyticsViewHolder {
        return DashboardAnalyticsViewHolder(
            RowMonitorDataItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return dashboardAnalyticsList.size
    }

    override fun onBindViewHolder(holder: DashboardAnalyticsViewHolder, position: Int) {
        val item = dashboardAnalyticsList[position]
        holder.bind(item)
    }

    inner class DashboardAnalyticsViewHolder(
        private val binding: RowMonitorDataItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WipPo) {
            binding.apply {
                textSerialNumber.text = itemCount.toString()
                textBuyer.text=item.buyerName
                textStyle.text = item.styleName
                textPo.text =item.poNumber
                textColor.text = item.colorName
                textLineCumOut.text = item.lineCumOut.toString()
                textIineCunIn.text = item.lineCumIn.toString()
                textWip.text = item.lineWip.toString()
                textReject.text = item.totalReject.toString()
            }


        }
    }
}