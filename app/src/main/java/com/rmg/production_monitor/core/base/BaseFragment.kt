package com.faisal.quc.core.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.faisal.quc.R
import com.faisal.quc.core.extention.gone

import com.faisal.quc.core.extention.show
import com.faisal.quc.core.extention.showNoInternetConnectionDialog
import com.faisal.quc.core.extention.toast
import com.faisal.quc.core.listener.LoaderController
import com.faisal.quc.core.listener.ToolBarController
import com.faisal.quc.core.managers.NetworkManager
import com.faisal.quc.databinding.DialogNoNetworkFoundBinding
import com.faisal.quc.view.MainActivity
import javax.inject.Inject

abstract class BaseFragment<Vb:ViewBinding> : Fragment() , LoaderController, ToolBarController {


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

            showNoInternetConnectionDialog(requireContext(),
                onRetryButtonClick = {
                    networkChecker { getData() }
                }
                )


        }
    }

    fun showTokenExpiredToast(){
        requireContext().resources.getString(R.string.login_expired).toast()

    }



    override fun showLoader() {

//        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
//        loaderDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
//     //   val loaderView: View =LayoutLoaderBinding.inflate(layoutInflater).root
//        val loaderView: View = LayoutInflater.from(requireContext()).inflate(R.layout.layout_loader, null)
//
//        loaderView.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        // Set background drawable to a transparent background
//      //  loaderView.background = ContextCompat.getDrawable(requireContext(), R.drawable.tras_bg)
//       // Create a transparent background drawable
//       // val transparentBackground = ColorDrawable(Color.TRANSPARENT)
//
//// Set the background drawable to the loader dialog
////        val inset = resources.getDimensionPixelOffset(R.dimen.load_size)
////        val drawable = InsetDrawable(transparentBackground, inset, inset, inset, inset)
////        loaderView.background = drawable
//// Set the loader view to the AlertDialog
//        alertDialogBuilder.setView(loaderView)
//
//        loaderDialog = alertDialogBuilder.create()
//        // todo change it later
//        loaderDialog!!.setCancelable(true)
//        loaderDialog!!.show()

        // chash issu solved
        loaderDialog = context?.let { Dialog(it) }
        // todo excape function

        loaderDialog?.requestWindowFeature(1)
        loaderDialog?.setContentView(R.layout.dialog_loader)
        loaderDialog?.setCanceledOnTouchOutside(false)
        loaderDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        loaderDialog?.show()

        loaderDialog?.findViewById<View>(android.R.id.content)?.postDelayed({
            loaderDialog?.dismiss()
        }, 6000)

//        if (loaderDialog?.isShowing == true) {
//            val handler = Handler(Looper.getMainLooper())
//            val runnable = Runnable {
//                if (loaderDialog.isShowing) {
//                    loaderDialog.dismiss()
//                }
//            }
//            loaderDialog.setOnDismissListener {
//                handler.removeCallbacks(runnable)
//            }
//            handler.postDelayed(runnable, 2000)
//        }
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

    override fun setPageTitle(title: String) {
        val activity=requireActivity() as MainActivity
        activity.binding.toolBar.setTitle(title)
    }


    override fun hideToolbar() {
        val activity=requireActivity() as MainActivity
        activity.binding.toolBar.gone()
        activity.binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun showToolbar() {
        val activity=requireActivity() as MainActivity
        activity.binding.toolBar.show()
        activity.binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun closeDrawer() {
        val activity = requireActivity() as MainActivity
        activity.binding.toolBar.navigationIcon = null
        activity.binding.toolBar.setNavigationOnClickListener {
            activity.binding.drawerLayout.closeDrawers()
        }
    }

    override fun openDrawer() {

        val activity = requireActivity() as MainActivity
        activity.binding.toolBar.setNavigationIcon(R.drawable.ic_menu)
        activity.binding.toolBar.setNavigationOnClickListener {
            activity.binding.drawerLayout.open()
            activity.binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }
}