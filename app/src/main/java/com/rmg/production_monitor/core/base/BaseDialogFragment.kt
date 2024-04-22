package com.rmg.production_monitor.core.base


import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
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
