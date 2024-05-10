package com.rmg.production_monitor.core.base

import android.app.Dialog
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.rmg.production_monitor.MainActivity
import com.rmg.production_monitor.R
//import com.rmg.production_monitor.core.extention.showNoInternetConnectionDialog
import com.rmg.production_monitor.core.listener.LoaderController
import com.rmg.production_monitor.core.listener.ToolBarController
import com.rmg.production_monitor.core.managers.network.NetworkManager
import dagger.hilt.android.AndroidEntryPoint


import javax.inject.Inject


abstract class BaseActivity<Vb:ViewBinding> : AppCompatActivity() , LoaderController {


    private  var loaderDialog: Dialog?=null
   // private var loaderDialog: AlertDialog? = null
    private var _binding: Vb? = null
    val binding: Vb get() = _binding!!

    private var _argumentOfFragment: Bundle? = null

    @Inject lateinit var networkManager: NetworkManager

    var argumentOfFragment: Bundle
        get() = _argumentOfFragment!!                                  // getter
        set(argument: Bundle) { _argumentOfFragment = argument }      // setter

    abstract fun getViewBinding(inflater: LayoutInflater): Vb
    protected open fun setupObserver() {}
    protected open fun initializeData() {}
   // protected abstract fun setupUI()
    protected open fun callInitialApi() {}

     protected open fun setDrawerLocked() {}
     protected open fun setListener() {}
     protected open fun setUpRecycleView() {}
    protected open fun setUpAdapter() {}
    protected open fun backPressButtonPressed() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding(layoutInflater)
        setContentView(binding.root)

        initializeData()

        setListener()

        setUpRecycleView()

        setUpAdapter()

        callInitialApi()

        setupObserver()

        setDrawerLocked()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            argumentOfFragment=it
//        }
//
//        val onBackPressedCallback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                backPressButtonPressed()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
//    }






   fun networkChecker(getData: () -> Unit) {
        if (networkManager.hasInternetConnection()) {

            getData()
        } else {

//            showNoInternetConnectionDialog(requireContext(),
//                onRetryButtonClick = {
//                    networkChecker { getData() }
//                }
//                )


        }
    }

    fun showTokenExpiredToast(){
       // requireContext().resources.getString(R.string.login_expired).toast()

    }



    override fun showLoader() {


        // chrash issue solved
        loaderDialog = this.let { Dialog(it) }


        loaderDialog.let {
            it?.apply {
                this.requestWindowFeature(1)
                this.setContentView(R.layout.dialog_loader)
                this.setCanceledOnTouchOutside(false)
                this.window?.setBackgroundDrawableResource(android.R.color.transparent)
                this.show()
            }
        }



        //  delay millisecond should be constant
        loaderDialog?.findViewById<View>(android.R.id.content)?.postDelayed({
            loaderDialog?.dismiss()
        }, LoaderController.LOAD_TIMER)


    }

    override fun hideLoader() {
        if (loaderDialog == null) {
            return
        }
        loaderDialog?.hide()
        loaderDialog?.dismiss()
        loaderDialog?.cancel()
        loaderDialog=null
    }








}