package com.rmg.production_monitor.core.base


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.rmg.production_monitor.R
import com.rmg.production_monitor.core.Config
import com.rmg.production_monitor.core.data.Ping
import com.rmg.production_monitor.core.extention.log
import com.rmg.production_monitor.core.extention.showNoInternetConnectionDialog
import com.rmg.production_monitor.core.listener.LoaderController
import com.rmg.production_monitor.core.managers.network.NetworkManager
import com.rmg.production_monitor.core.managers.network.PingManager
import com.rmg.production_monitor.view.dialog.NoInternetConnectionDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URI
import java.net.URL
import javax.inject.Inject


abstract class BaseFragment<Vb:ViewBinding> : Fragment() , LoaderController {


    private  var loaderDialog: Dialog?=null
   // private var loaderDialog: AlertDialog? = null
    private var _binding: Vb? = null
    val binding: Vb get() = _binding!!

    private var pageTitle:String=""
    private var _argumentOfFragment: Bundle? = null





    @Inject lateinit var networkManager: NetworkManager
    @Inject lateinit var pinManager: PingManager
    private lateinit var dialog: NoInternetConnectionDialog
//    private var ping:Ping?=null

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

        dialog = NoInternetConnectionDialog(requireContext(), R.style.NetworkConnectivityAlertDialog,null,null)

    }





   fun networkChecker(getData: () -> Unit) {
       lifecycleScope.launch(Dispatchers.IO) {
          val ping=pinManager.doPing(URL(Config.BASE_URL))
           // invoke some suspend functions or execute potentially long running code

           // to switch context in this case and be able to update UI
           withContext(Dispatchers.Main) {
               // updateUI
               if (ping.ip.isNotEmpty()){
                   try {
                       dialog.dismiss()
                   } catch (e: Exception) {
                       e.printStackTrace()
                       e.toString().log("dialog")
                   }
                   getData()
               }else{
                   try {
                       dialog.show()
                   } catch (e: Exception) {
                       e.printStackTrace()
                       e.toString().log("dialog")
                   }
//                   showNoInternetConnectionDialog(requireContext(),
//                       onRetryButtonClick = {
//                           networkChecker { getData() }
//                       }
//                   )
               }
           }
       }
//       CoroutineScope(Dispatchers.IO).launch{
//           ping=pinManager.doPing(URL(Config.BASE_URL))
////           if (networkManager.hasInternetConnection()) {
////
////           } else {
////               showNoInternetConnectionDialog(requireContext(),
////                   onRetryButtonClick = {
////                       networkChecker { getData() }
////                   }
////               )
////
////           }
//       }
//       if (ping?.isServerAlive == true){
//           getData()
//       }else{
//           showNoInternetConnectionDialog(requireContext(),
//               onRetryButtonClick = {
//                   networkChecker { getData() }
//               }
//           )
//       }

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