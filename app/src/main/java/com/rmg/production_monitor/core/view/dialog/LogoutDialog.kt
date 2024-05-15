package com.rmg.production_monitor.core.view.dialog


import android.app.AlertDialog
import android.content.Context
import com.rmg.production_monitor.core.base.BaseDialogFragment
import com.rmg.production_monitor.databinding.DialogLogoutBinding


class LogoutDialog(  context: Context,   themeResId:Int): BaseDialogFragment<DialogLogoutBinding>(context,themeResId) {
    private var onPositiveButtonClick: (() -> Unit)? = null
    private var onNegativeButtonClick: (() -> Unit)? = null



    constructor( context: Context,themeResId:Int , onPositiveButtonClick: (() -> Unit)? = null,
                 onNegativeButtonClick: (() -> Unit)? = null) : this(context,themeResId) {

        this.onPositiveButtonClick = onPositiveButtonClick
        this.onNegativeButtonClick = onNegativeButtonClick
    }

    override fun getViewBinding(): DialogLogoutBinding {
        return DialogLogoutBinding.inflate(layoutInflater)


    }

    override fun viewDidCreated() {

    }

    override fun setViewListener() {
        binding.btnNo.setOnClickListener {
            dismiss()
            onNegativeButtonClick?.invoke()
        }
        binding.btnYes.setOnClickListener {
            dismiss()
            onPositiveButtonClick?.invoke()
        }
    }


}