package com.rmg.production_monitor.core.managers.network

interface NetworkManager {

    fun hasInternetConnection(): Boolean

    fun getNetworkType():String
}