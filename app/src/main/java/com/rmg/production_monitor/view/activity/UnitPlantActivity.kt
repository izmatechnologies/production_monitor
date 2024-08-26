package com.rmg.production_monitor.view.activity


import android.content.Intent
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rmg.production_monitor.R
import com.rmg.production_monitor.core.Constants
import com.rmg.production_monitor.core.base.BaseActivity
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.core.managers.preference.AppPreferenceImpl
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
    private lateinit var selectedLineName:String
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
                authenticationViewModel.saveUnit(selectedUnit)
                authenticationViewModel.savePlant(selectedPlant)
                authenticationViewModel.saveLine(selectedline)
                "save".toast()
                authenticationViewModel.setPlantLineName(selectedLineName)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }



        binding.autoCompleteUnit.setOnItemClickListener { _, _, position, _ ->
            selectedUnit = unitList[position].plantUnitId?:0
//            if (selectedUnit != -1) {
//                binding.text1.text = "Plant Unit Name  : ${unitList[position].plantUnitName.toString()}"
//            }
        }

        binding.autoCompletePlant.setOnItemClickListener { _, _, position, _ ->
            selectedPlant = plantList[position].plantId?:0
//            if (selectedPlant != -1) {
//                binding.text2.text = "Plant  Name  :" + plantList[position].plantName.toString()
//            }
        }

        binding.autoCompleteLine.setOnItemClickListener { _, _, position, _ ->
            selectedline = lineList[position].lineId?:0
            selectedLineName=lineList[position].lineName.toString()
//            if (selectedline != -1) {
//                binding.text3.text = "User Line  Name  : $selectedLineName"
//            }
        }

    }

    override fun setUpAdapter() {
        super.setUpAdapter()

        val adapterUnit = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, unitList)
        binding.autoCompleteUnit.setAdapter(adapterUnit)


        val adapterPlant = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, plantList)
        binding.autoCompletePlant.setAdapter(adapterPlant)

        val adapterLine = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, lineList)
        binding.autoCompleteLine.setAdapter(adapterLine)
    }


    private fun validation(): Boolean {
        // todo validate to model view
        if (selectedPlant== -1) {
            "Select Plant Name".toast()
            return false
        }
        if (selectedUnit == -1) {
            "Select Plant Unit  Name".toast()
            return false
        }
        if (selectedline== -1) {
            "Select User Line Name".toast()
            return false
        }
        return true
    }


}