package com.rmg.production_monitor.core.managers

interface NetworkManager {

    fun hasInternetConnection(): Boolean

    fun getNetworkType():String
}