package com.rmg.production_monitor.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rmg.production_monitor.R
import com.rmg.production_monitor.databinding.DeffectOperationsLayoutBinding
import com.rmg.production_monitor.models.remote.quality.TopProductionsIssue

class TopProductionsIssueAdapter(
    private val context:Context,
    private val topIssues: List<TopProductionsIssue>
) : RecyclerView.Adapter<TopProductionsIssueAdapter.DefectOperationsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefectOperationsViewHolder {
        return DefectOperationsViewHolder(DeffectOperationsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return topIssues.size
    }

    override fun onBindViewHolder(holder: DefectOperationsViewHolder, position: Int) {
        val item = topIssues[position]
        holder.bind(item)
    }

    inner class DefectOperationsViewHolder(
        private val binding: DeffectOperationsLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TopProductionsIssue) {
            binding.apply {
                tvIssueName.text=item.name
                tvIssueValue.text=item.value.toString()
                if (topIssues.size - 1 == absoluteAdapterPosition) {
                    viewBottom.visibility = View.GONE
                } else viewBottom.visibility = View.VISIBLE

                if (absoluteAdapterPosition==0){
                    tvIssueValue.background=ContextCompat.getDrawable(context,R.drawable.outline_box_border)
                    tvIssueValue.setTextColor(ContextCompat.getColor(context,R.color.d44d25))
                }else if(absoluteAdapterPosition==1){
                    tvIssueValue.background=ContextCompat.getDrawable(context,R.drawable.outline_box_border_2nd)
                    tvIssueValue.setTextColor(ContextCompat.getColor(context,R.color.cc7944))
                }else{
                    tvIssueValue.background=ContextCompat.getDrawable(context,R.drawable.outline_box_border_3rd)
                    tvIssueValue.setTextColor(ContextCompat.getColor(context,R.color.b4a14f))
                }
            }
        }
    }
}