package com.rmg.production_monitor.core.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
//import com.rmg.production_monitor.core.extention.showNoInternetConnectionDialog
import com.rmg.production_monitor.core.listener.LoaderController
import com.rmg.production_monitor.core.managers.network.NetworkManager


import javax.inject.Inject


abstract class BaseFragment<Vb:ViewBinding> : Fragment() , LoaderController {


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
        arguments?.let {
            argumentOfFragment=it
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backPressButtonPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = getViewBinding(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeData()

        setUpRecycleView()

        setListener()

        setupObserver()

        callInitialApi()

        setDrawerLocked()



    }


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


//        // todo change it later


        // chash issu solved
//        loaderDialog = context?.let { Dialog(it) }
//        // todo excape function
//
//        loaderDialog?.requestWindowFeature(1)
//        loaderDialog?.setContentView(R.layout.dialog_loader)
//        loaderDialog?.setCanceledOnTouchOutside(false)
//        loaderDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
//        loaderDialog?.show()
//
//        loaderDialog?.findViewById<View>(android.R.id.content)?.postDelayed({
//            loaderDialog?.dismiss()
//        }, 6000)


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