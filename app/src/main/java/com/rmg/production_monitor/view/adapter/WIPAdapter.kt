package com.rmg.production_monitor.view.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rmg.production_monitor.databinding.RowItemWipBinding

import com.rmg.production_monitor.models.remote.dasboard.WipPo
import javax.inject.Inject


class WIPAdapter  @Inject constructor(): RecyclerView.Adapter<WIPAdapter.ActiveConsultantViewHolder>() {
 //   var listener: MediaRouter.SimpleCallback<QcStatusPayload>? = null

    inner class ActiveConsultantViewHolder(val binding: RowItemWipBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<WipPo>() {
        override fun areItemsTheSame(oldItem: WipPo, newItem: WipPo): Boolean {
            return oldItem.buyerId == newItem.buyerId
        }

        override fun areContentsTheSame(oldItem: WipPo, newItem: WipPo): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveConsultantViewHolder {

        val binding = RowItemWipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActiveConsultantViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ActiveConsultantViewHolder, position: Int) {
        val qcStatus = differ.currentList.get(position)
        val ctx = holder.binding.root.context
        holder.binding.apply {
            var positionSerial= position+1
                textSerialNumber.text =positionSerial.toString()
                textBuyer.text=qcStatus.buyerName
                textStyle.text = qcStatus.styleName
                textPo.text =qcStatus.poNumber
                textColor.text = qcStatus.colorName
                textLineCumOut.text = qcStatus.lineCumOut.toString()
                textIineCunIn.text = qcStatus.lineCumIn.toString()
                textWip.text = qcStatus.lineWip.toString()
            textReject.text = qcStatus.totalReject.toString()


        }
    }
}