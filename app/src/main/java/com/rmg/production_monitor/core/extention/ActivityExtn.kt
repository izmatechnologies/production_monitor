package com.rmg.production_monitor.core.extention

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.rmg.production_monitor.R
import com.rmg.production_monitor.core.view.dialog.LogoutDialog
import com.rmg.production_monitor.view.dialog.NoInternetConnectionDialog


// todo don't use as extention funtion use as dependant inject ion
//fun Activity.networkChecker(getData: () -> Unit) {
//    if (true) {
//        getData()
//    } else {
//        val builder = AlertDialog.Builder(this, R.style.NetworkConnectivityAlertDialog).create()
//        val view = layoutInflater.inflate(R.layout.dialog_no_network_found, null)
//        builder.setView(view)
//        builder.setCanceledOnTouchOutside(false)
//        builder.show()
//
//        val retryButton = view.findViewById<View>(R.id.retryButton)
//        val cancelButton = view.findViewById<View>(R.id.cancelButton)
//
//        retryButton.setOnClickListener {
//            builder.dismiss()
//            networkChecker { getData() }
//        }
//        cancelButton.setOnClickListener { builder.dismiss() }
//    }
//}
// todo new  lout out button

fun Activity.showNoInternetConnectionDialog(
    context: Context,
    onRetryButtonClick: (() -> Unit)? = null,
    onCancelButtonClick: (() -> Unit)? = null
) {

    val dialog = NoInternetConnectionDialog(
        context, R.style.NetworkConnectivityAlertDialog,
        onRetryButtonClick, onCancelButtonClick
    )


    try {
        dialog.show()
    } catch (e: Exception) {
        e.printStackTrace()
        e.toString().log("dialog")
    }
}

fun Activity.showLogoutDialog(
    context: Context,
    onYesButtonClick: () -> Unit,
    onNoButtonClick: (() -> Unit)? = null
) {

    val dialog = LogoutDialog(
        context, R.style.CustomAlertDialog,
        onYesButtonClick, onNoButtonClick
    )

    dialog.setCanceledOnTouchOutside(false)
    try {
        dialog.show()
    } catch (e: Exception) {
        e.printStackTrace()
        e.toString().log("dialog")
    }
}


