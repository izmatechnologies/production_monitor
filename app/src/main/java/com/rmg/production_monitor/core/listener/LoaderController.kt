package com.rmg.production_monitor.core.listener

interface LoaderController {
    companion object{
        private const val LOAD_TIMER_IN_SECOND:Int=6
        private const  val MILLI_SECOND:Int=1000
        const val LOAD_TIMER:Long=LOAD_TIMER_IN_SECOND.toLong()*MILLI_SECOND.toLong()
    }
    fun showLoader();

    fun hideLoader();
}