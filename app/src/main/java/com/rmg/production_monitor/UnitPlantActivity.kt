package com.rmg.production_monitor

import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.rmg.production_monitor.core.base.BaseActivity
import com.rmg.production_monitor.databinding.ActivityUnitPlantBinding

class UnitPlantActivity : BaseActivity<ActivityUnitPlantBinding>() {


    override fun getViewBinding(inflater: LayoutInflater): ActivityUnitPlantBinding {
      return  ActivityUnitPlantBinding.inflate(inflater)
    }

    override fun initializeData() {
        super.initializeData()


    }



}