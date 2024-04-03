package com.faisal.quc.core.base


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding



abstract class BaseDialogFragment<VB : ViewBinding>  constructor(context:Context): AlertDialog(context) {

    constructor(context:Context, themeResId:Int): this(context)
    private var _binding: VB? = null

    protected val binding: VB
        get() = _binding!!

    protected abstract fun getViewBinding(): VB
    protected abstract fun viewDidCreated()
    protected abstract fun setViewListener()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()

        setContentView(_binding!!.root)
        viewDidCreated()
        setViewListener()

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }
}
