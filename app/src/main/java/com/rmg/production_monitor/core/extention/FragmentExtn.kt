package com.rmg.production_monitor.core.extention

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.rmg.production_monitor.R

import com.rmg.production_monitor.core.Constants
import com.rmg.production_monitor.core.view.dialog.LogoutDialog
import com.rmg.production_monitor.databinding.DialogNoNetworkFoundBinding
import com.rmg.production_monitor.view.dialog.NoInternetConnectionDialog


fun Fragment.showLogoutDialog(
    context: Context,
    onYesButtonClick: (() -> Unit)? = null,
    onNoButtonClick: (() -> Unit)? = null
) {

    val dialog = LogoutDialog(
        context, R.style.CustomAlertDialog,
        onYesButtonClick, onNoButtonClick
    )


    try {
        dialog.show()
    } catch (e: Exception) {
        e.printStackTrace()
        e.toString().log("dialog")
    }
}


fun Fragment.showNoInternetConnectionDialog(
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

