package com.faisal.quc.core.managers

interface NetworkManager {

    fun hasInternetConnection(): Boolean

    fun getNetworkType():String
}