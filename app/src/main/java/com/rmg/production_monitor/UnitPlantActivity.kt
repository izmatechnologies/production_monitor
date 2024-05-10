package com.rmg.production_monitor

import android.R
import android.content.Intent
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.compose.ui.unit.Constraints
import androidx.core.widget.doAfterTextChanged
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rmg.production_monitor.core.Constants
import com.rmg.production_monitor.core.base.BaseActivity
import com.rmg.production_monitor.core.extention.enable
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.ActivityUnitPlantBinding
import com.rmg.production_monitor.models.remote.authentication.UserLine
import com.rmg.production_monitor.models.remote.authentication.UserPlant
import com.rmg.production_monitor.models.remote.authentication.UserPlantUnit
import com.rmg.production_monitor.viewModel.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Type

@AndroidEntryPoint
class UnitPlantActivity : BaseActivity<ActivityUnitPlantBinding>() {
    private val authenticationViewModel by viewModels<AuthenticationViewModel>()
    private var unitList = emptyList<UserPlantUnit>()
    private var selectedUnit: Int = -1
    private var plantList = emptyList<UserPlant>()
    private var selectedPlant: Int = -1

    private var lineList = emptyList<UserLine>()
    private var selectedline: Int = -1
    override fun getViewBinding(inflater: LayoutInflater): ActivityUnitPlantBinding {
        return ActivityUnitPlantBinding.inflate(inflater)
    }

    override fun initializeData() {
        super.initializeData()

        val unitKey = Constants.FragmentKey.UNIT_LIST
        val plantKey = Constants.FragmentKey.PLANT_LIST
        val lineInKey = Constants.FragmentKey.LINE_IN_LIST

        val intent = intent
        val unitkeyList = intent.getStringExtra(unitKey) ?: ""
        val plantkeyList = intent.getStringExtra(plantKey) ?: ""
        val lineInkeyList = intent.getStringExtra(lineInKey) ?: ""

        val unitListType: Type = object : TypeToken<List<UserPlantUnit>>() {}.type
        unitList = Gson().fromJson(unitkeyList, unitListType)

        val plantListType: Type = object : TypeToken<List<UserPlant>>() {}.type
        plantList = Gson().fromJson(plantkeyList, plantListType)

        val lineInListType: Type = object : TypeToken<List<UserLine>>() {}.type
        lineList = Gson().fromJson(lineInkeyList, lineInListType)


    }

    override fun setListener() {
        super.setListener()

        binding.btnComplete.setOnClickListener {
            if (validation()) {
                authenticationViewModel.saveUnit(selectedUnit.toString())
                authenticationViewModel.savePlant(selectedPlant.toString())
                authenticationViewModel.saveLine(selectedline.toString())
                "save".toast()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }



        binding.autoCompleteUnit.setOnItemClickListener { _, _, position, _ ->
            selectedUnit = unitList[position].plantUnitId!!
            if (selectedUnit != -1) {
                binding.text1.text =
                    "Plant Unit Name  :" + unitList[position].plantUnitName.toString()
            }
        }

        binding.autoCompletePlant.setOnItemClickListener { _, _, position, _ ->
            selectedPlant = plantList[position].plantId!!
            if (selectedPlant != -1) {
                binding.text2.text = "Plant  Name  :" + plantList[position].plantName.toString()
            }
        }

        binding.autoCompleteLine.setOnItemClickListener { _, _, position, _ ->
            selectedline = lineList[position].lineId!!
            if (selectedline != -1) {
                binding.text3.text = "User Line  Name  :" + lineList[position].lineName.toString()
            }
        }

    }

    override fun setUpAdapter() {
        super.setUpAdapter()

        val adapterUnit = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, unitList)
        binding.autoCompleteUnit.setAdapter(adapterUnit)


        val adapterPlant = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, plantList)
        binding.autoCompletePlant.setAdapter(adapterPlant)

        val adapterLine = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, lineList)
        binding.autoCompleteLine.setAdapter(adapterLine)
    }


    private fun validation(): Boolean {
        // todo validate to model view
        if (binding.text2.text.toString().isEmpty()) {
            "Select Plant Name".toast()
            return false
        }
        if (binding.text1.text.toString().isEmpty()) {
            "Select Plant Unit  Name".toast()
            return false
        }
        if (binding.text3.text.toString().isEmpty()) {
            "Select User Line Name".toast()
            return false
        }
        return true
    }


}