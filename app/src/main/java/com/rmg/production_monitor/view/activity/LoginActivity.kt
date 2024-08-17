package com.rmg.production_monitor.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.faisal.quc.models.remote.authentication.AuthenticationRequest
import com.google.gson.Gson
import com.rmg.production_monitor.R
import com.rmg.production_monitor.core.Constants
import com.rmg.production_monitor.core.base.BaseActivity
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.extention.enable
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.ActivityLoginBinding
import com.rmg.production_monitor.viewModel.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity :BaseActivity<ActivityLoginBinding>() {

    private val authenticationViewModel by viewModels<AuthenticationViewModel>()
    override fun getViewBinding(inflater: LayoutInflater): ActivityLoginBinding {
      return ActivityLoginBinding.inflate(inflater)


    }

    override fun initializeData() {
        super.initializeData()

        val token = authenticationViewModel.getAuthToken()

        if (!token.isNullOrEmpty()) {
            val intent: Intent = Intent(
                this,
                MainActivity::class.java
            )
            startActivity(intent)
        }


        binding.inputEmail.setText(resources.getString(R.string.swing_in_user_name))
        binding.inputPassword.setText(resources.getString(R.string.swing_in_password))
        binding.btnSettings.setOnClickListener {
            val intent = Intent(this, UrlSetUpActivity::class.java)
            startActivity(intent)
        }

    }

    override fun setListener() {
        super.setListener()

        binding.inputPassword.doAfterTextChanged {
            if (binding.inputEmail.text.toString()
                    .isNotEmpty() && binding.inputPassword.text.toString().isNotEmpty()
            ) {
                binding.btnLogin.enable(true)
            } else binding.btnLogin.enable(false)
        }
        binding.btnLogin.setOnClickListener {

            if (validation()) {

                networkChecker {
                    val userName = binding.inputEmail.text.toString()
                    val password = binding.inputPassword.text.toString()

                    val requestBodyJson = AuthenticationRequest(userName, password)
                    authenticationViewModel.doAuthenticate(requestBodyJson)

                }

            }
        }
    }


    override fun setupObserver() {
        super.setupObserver()
        authenticationViewModel.authenticate.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                      hideLoader()
                    //val responseData = response.data as? AuthenticateModel ?: return
                    val token = it.data?.authenticatePayload?.accessToken
                    if (!token.isNullOrEmpty()) {
                        // save it lateer
                        authenticationViewModel.storeAuthenticationToken(token)

                        val userTypeName = it.data.authenticatePayload.userTypeName
                        var userName = it.data.authenticatePayload.userName

//                        authenticationViewModel.saveUserName(userName)
//                        authenticationViewModel.saveUserType(userTypeName)

                            val intent = Intent(this, UnitPlantActivity::class.java)
                            val bundle = Bundle()
                            bundle.putString(Constants.FragmentKey.LINE_IN_LIST, Gson().toJson(it.data.authenticatePayload.userLines))
                            bundle.putString(Constants.FragmentKey.UNIT_LIST, Gson().toJson(it.data.authenticatePayload.userPlantUnits))
                            bundle.putString(Constants.FragmentKey.PLANT_LIST, Gson().toJson(it.data.authenticatePayload.userPlants))
                            intent.putExtras(bundle)
                            startActivity(intent)

                    }
                }

                is NetworkResult.Error -> {
                      hideLoader()
                }

                is NetworkResult.Loading -> {
                     showLoader()
                }

                is NetworkResult.SessionOut -> {
                    // do nothing
//                 homeViewModel.clearAuthToken()
//                 (requireActivity() as MainActivity).logout()
                }

                else -> {}
            }
        }

    }

    private fun validation(): Boolean {
        // todo validate to model view
        if (binding.inputEmail.text.toString().isEmpty()) {
            binding.inputEmail.error = "required"

            "Something wrong! please try again".toast()
            return false
        }
        if (binding.inputPassword.text.toString().isEmpty()) {

            "Something wrong! please try again".toast()
            binding.inputPassword.error = "required"
            binding.inputPassword.requestFocus()
            return false
        }
        return true
    }



}



