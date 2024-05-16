package com.rmg.production_monitor.core.extention

import android.content.Context
import androidx.fragment.app.Fragment
import com.rmg.production_monitor.R

import com.rmg.production_monitor.core.Constants


//
//fun Fragment.showLogoutDialog(
//    context: Context,
//    onYesButtonClick: (() -> Unit)? = null,
//    onNoButtonClick: (() -> Unit)? = null
//) {
//
//    val dialog = LogoutDialog(
//        context, R.style.CustomAlertDialog,
//        onYesButtonClick, onNoButtonClick
//    )
//
//
//    try {
//        dialog.show()
//    } catch (e: Exception) {
//        e.printStackTrace()
//        e.toString().log("dialog")
//    }
//}
//
//
//fun Fragment.showNoInternetConnectionDialog(
//context: Context,
//onRetryButtonClick: (() -> Unit)? = null,
//onCancelButtonClick: (() -> Unit)? = null
//) {
//
//    val dialog = NoInternetConnectionDialog(
//        context, R.style.NetworkConnectivityAlertDialog,
//        onRetryButtonClick, onCancelButtonClick
//    )
//
//
//    try {
//        dialog.show()
//    } catch (e: Exception) {
//        e.printStackTrace()
//        e.toString().log("dialog")
//    }
//}


//fun Fragment.networkChecker(getData: () -> Unit) {
//    //if (NetworkManagerImpl().hasInternetConnection()) {
//    if (true) {
//        getData()
//    } else {
//        val builder =
//            AlertDialog.Builder(requireContext(), R.style.NetworkConnectivityAlertDialog).create()
//        val bind: DialogNoNetworkFoundBinding = DialogNoNetworkFoundBinding.inflate(layoutInflater)
//
//
//
//        bind.retryButton.setOnClickListener {
//            builder.dismiss()
//            networkChecker { getData() }
//        }
//        bind.cancelButton.setOnClickListener { builder.dismiss() }
//
//        builder.setCanceledOnTouchOutside(false)
//
//        builder.setView(bind.root)
//        builder.show()
//
//
//    }
//}

//fun Fragment.showOperationBottomSheet(operations: ArrayList<QcOperationEntity>,
//                                      onPositiveButtonClick: ((QcOperationEntity?) -> Unit)? = null, ) {
//
//    val dialog = QcOperationBottomSheet(operations, onPositiveButtonClick)
//
//
//    try{
//        dialog.show(childFragmentManager, Constants.BottomSheetTag.OPERATION_B_S)
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//}
//
//fun Fragment.showIssueBottomSheet(
//    issuesList: List<QcIssueEntity?>,
//    onPositiveButtonClick: ((QcIssueEntity?) -> Unit)? = null,
//    onCloseButtonClick: (() -> Unit)? = null,
//) {
//
//    val dialog = QcIssuesBottomSheet(issuesList,onPositiveButtonClick,onCloseButtonClick)
//    try {
//        dialog.show(childFragmentManager, Constants.BottomSheetTag.ISSUE_B_S)
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//}