package com.rmg.production_monitor.core.managers

import com.rmg.production_monitor.core.extention.log
import java.net.InetAddress
import java.net.Socket
import java.net.URL
import javax.inject.Inject

class PingManagerImp  @Inject constructor(
    private val networkManager: NetworkManager
) : PingManager {


    override fun doPing(url: URL): Ping {
        var ping = Ping()
        if (networkManager.hasInternetConnection()){
            ping.net= networkManager?.getNetworkType().toString()
            try {
                val start=System.currentTimeMillis()
                var hostAddress:String = InetAddress.getByName(url.host).hostAddress?.toString() ?: "dim"
                val dnsResolved=System.currentTimeMillis()
                val socket:Socket= Socket(hostAddress,url.port)
                socket.close()
                val probeFinish=System.currentTimeMillis()
                ping.dns = (dnsResolved - start)
                ping.cnt =  (probeFinish - dnsResolved)
                ping.host = url.host;
                ping.ip = hostAddress;
            }catch ( ex:Exception) {
                "Unable to ping".log("ping")
            }
        }

            ping.toString().log("ping")
        return ping
    }
}

