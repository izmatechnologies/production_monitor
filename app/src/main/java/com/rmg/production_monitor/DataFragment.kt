package com.rmg.production_monitor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.databinding.FragmentDataBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DataFragment :BaseFragment<FragmentDataBinding>() {
    override fun getViewBinding(inflater: LayoutInflater): FragmentDataBinding {
       return FragmentDataBinding.inflate(inflater)
    }


}