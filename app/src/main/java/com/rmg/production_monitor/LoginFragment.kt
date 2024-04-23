package com.rmg.production_monitor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.faisal.quc.models.remote.authentication.AuthenticationRequest
import com.rmg.production_monitor.core.Config
import com.rmg.production_monitor.core.Constants
import com.rmg.production_monitor.core.base.BaseFragment
import com.rmg.production_monitor.core.data.NetworkResult
import com.rmg.production_monitor.core.extention.enable
import com.rmg.production_monitor.core.extention.toast
import com.rmg.production_monitor.databinding.FragmentDataBinding
import com.rmg.production_monitor.databinding.FragmentLoginBinding
import com.rmg.production_monitor.viewModel.AuthenticationViewModel
import com.rmg.production_monitor.viewModel.DashboardViewModel
import com.rmg.production_monitor.viewModel.QualityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val authenticationViewModel by viewModels<AuthenticationViewModel>()


    override fun getViewBinding(inflater: LayoutInflater): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater)
    }


    override fun initializeData() {
        super.initializeData()

        val token = authenticationViewModel.getAuthToken()
        val userType = authenticationViewModel.getUserType()

        if (!token.isNullOrEmpty()) {
            if (userType == Constants.UserType.SWING_LINE_IN_TYPE_USER.value) {
                findNavController().popBackStack()
              //  findNavController().navigate(R.id.sewingLineFragment)
            } else {
                findNavController().popBackStack()
                //findNavController().navigate(R.id.newSelectPOFragment)
            }
        }


//        var user_name = ""
//        var password = ""
//        if (BuildConfig.DEBUG){
//            if (Config.CORE_OPERATION_MODE == Config.OperationModes.QC_OPERATION_MODE) {
//                user_name = requireContext().resources.getString(R.string.qc_user_name)
//                password = requireContext().resources.getString(R.string.qc_password)
//            } else {
//                user_name = requireContext().resources.getString(R.string.swing_in_user_name)
//                password = requireContext().resources.getString(R.string.swing_in_password)
//            }
//        }
//
//
//
//        binding.inputEmail.setText(user_name)
//        binding.inputPassword.setText(password)

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

                    val  requestBodyJson=AuthenticationRequest(userName,password)
                    networkChecker {
                        authenticationViewModel.doAuthenticate(requestBodyJson)
                    }

                }

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


        authenticationViewModel.authenticate.observe(viewLifecycleOwner) {
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

                        authenticationViewModel.saveUserName(userName)
                        authenticationViewModel.saveUserType(userTypeName)


                        // todo user type call constant value
                        if (userTypeName == "UT_03") {
                            findNavController().popBackStack()
                         //   findNavController().navigate(R.id.sewingLineFragment)
                        } else {
                            findNavController().popBackStack()
                         //   findNavController().navigate(R.id.newSelectPOFragment)
                        }

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