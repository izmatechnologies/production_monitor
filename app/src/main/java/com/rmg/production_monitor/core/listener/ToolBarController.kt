package com.rmg.production_monitor.core.listener

interface ToolBarController {
    fun setPageTitle(title: String);
    fun hideToolbar()

    fun showToolbar()

    fun openDrawer()
    fun closeDrawer()

}