package com.rmg.production_monitor

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.faisal.quc.models.remote.authentication.AuthenticationRequest
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


//        val token = authenticationViewModel.getAuthToken()
//        val userType = authenticationViewModel.getUserType()
//
//        if (!token.isNullOrEmpty()) {
//            if (userType == Constants.UserType.SWING_LINE_IN_TYPE_USER.value) {
//
//                val intent: Intent = Intent(
//                    this,
//                    MainActivity::class.java
//                )
//                startActivity(intent)
//                //  findNavController().navigate(R.id.sewingLineFragment)
//            } else {
//
//
//            }
//        }


        binding.inputEmail.setText(resources.getString(R.string.swing_in_user_name))
        binding.inputPassword.setText(resources.getString(R.string.swing_in_password))

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

                        authenticationViewModel.saveUserName(userName)
                        authenticationViewModel.saveUserType(userTypeName)


                        // todo user type call constant value
                        if (userTypeName == "UT_03") {
                            showAlertDialog()


//                            val intent: Intent = Intent(
//                                this,
//                                MainActivity::class.java
//                            )
//                            startActivity(intent)
                        } else {
                            //'   findNavController().popBackStack()
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

    private fun showAlertDialog() {

        val builder = AlertDialog.Builder(this)
            .create()
        val view = layoutInflater.inflate(R.layout.dialog_unit_plant,null)
      //  val  cancelBtn = view.findViewById<Button>(R.id.btn_cancel)
        val  okBtn = view.findViewById<Button>(R.id.btn_complete)

//        val  title = view.findViewById<TextView>(R.id.subtitle)
//        title.text=text
        builder.setView(view)
//        builder.setView(view)
//        cancelBtn.setOnClickListener {
//            builder.dismiss()
//        }
        okBtn.setOnClickListener {
            builder.dismiss()

        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

}



