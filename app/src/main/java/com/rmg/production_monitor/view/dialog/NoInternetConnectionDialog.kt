package com.rmg.production_monitor.view.dialog



import android.content.Context

import com.rmg.production_monitor.core.base.BaseDialogFragment
import com.rmg.production_monitor.databinding.DialogNoNetworkFoundBinding


class NoInternetConnectionDialog(context: Context, themeResId:Int): BaseDialogFragment<DialogNoNetworkFoundBinding>(context,themeResId) {
    private var onPositiveButtonClick: (() -> Unit)? = null
    private var onNegativeButtonClick: (() -> Unit)? = null



    constructor( context: Context,themeResId:Int , onPositiveButtonClick: (() -> Unit)? = null,
                 onNegativeButtonClick: (() -> Unit)? = null) : this(context,themeResId) {

        this.onPositiveButtonClick = onPositiveButtonClick
        this.onNegativeButtonClick = onNegativeButtonClick
    }

    override fun getViewBinding(): DialogNoNetworkFoundBinding {
        return DialogNoNetworkFoundBinding.inflate(layoutInflater)


    }

    override fun viewDidCreated() {

    }

    override fun setViewListener() {

        binding.retryButton.setOnClickListener {
            dismiss()
            onPositiveButtonClick?.invoke()
        }
    }


}