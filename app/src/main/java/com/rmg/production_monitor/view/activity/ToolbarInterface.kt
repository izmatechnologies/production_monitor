package com.rmg.production_monitor.view.activity

interface ToolbarInterface {

    fun onRefreshButtonClick()
    fun setPageName(title:String)
    fun getPageName():String
}