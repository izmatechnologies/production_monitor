package com.rmg.production_monitor

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rmg.production_monitor.core.base.BaseActivity
import com.rmg.production_monitor.core.extention.enable
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.ActivityUnitPlantBinding
import com.rmg.production_monitor.models.remote.authentication.UserPlant
import com.rmg.production_monitor.models.remote.authentication.UserPlantUnit
import com.rmg.production_monitor.viewModel.AuthenticationViewModel
import java.lang.reflect.Type

class UnitPlantActivity : BaseActivity<ActivityUnitPlantBinding>() {
    private val authenticationViewModel by viewModels<AuthenticationViewModel>()
    private var unitList = emptyList<UserPlantUnit>()
    private var selectedUnit: Int = -1
    private var plantList = emptyList<UserPlant>()
    private var selectedPlant: Int = -1
    override fun getViewBinding(inflater: LayoutInflater): ActivityUnitPlantBinding {
      return  ActivityUnitPlantBinding.inflate(inflater)
    }

    override fun initializeData() {
        super.initializeData()
//        binding.btnComplete.doAfterTextChanged {
//            if (binding.text1.text.toString()
//                    .isNotEmpty() && binding.text2.text.toString().isNotEmpty()
//            ) {
//                binding.btnComplete.enable(true)
//            } else binding.btnComplete.enable(false)
//        }

        val unitKey = "unit"
        val plantKey = "plant"
        val intent = intent
        val unitkeyList = intent.getStringExtra(unitKey)
        val plantkeyList = intent.getStringExtra(plantKey)

        val unitListType: Type = object : TypeToken<List<UserPlantUnit>>() {}.type
        unitList= Gson(). fromJson(unitkeyList, unitListType)

        val plantListType: Type = object : TypeToken<List<UserPlant>>() {}.type

        plantList= Gson(). fromJson(plantkeyList, plantListType)



    }
    override fun setListener() {
        super.setListener()

        binding.autoCompleteUnit.setOnItemClickListener { _, _, position, _ ->
            selectedUnit = unitList[position].plantUnitId!!
            if (selectedUnit != -1) {
                binding.text1.text="Plant Unit Name  :"+unitList[position].plantUnitName.toString()
            }
        }

        binding.autoCompletePlant.setOnItemClickListener { _, _, position, _ ->
            selectedPlant = unitList[position].plantUnitId!!
            if (selectedPlant != -1) {
                binding.text2.text="Plant  Name  :"+plantList[position].plantName.toString()
            }
        }

        if (selectedUnit != -1 && selectedPlant != -1){
            binding.btnComplete.setOnClickListener {
                authenticationViewModel.saveUnit(selectedUnit)
                authenticationViewModel.savePlant(selectedPlant)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }else{
            "Select Unit And Plant Name".toast()
        }

        }
    override fun setUpAdapter() {
        super.setUpAdapter()

        val adapterUnit = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, unitList)
        binding.autoCompleteUnit.setAdapter(adapterUnit)


        val adapterPlant = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, plantList)
        binding.autoCompletePlant.setAdapter(adapterPlant)
    }

}