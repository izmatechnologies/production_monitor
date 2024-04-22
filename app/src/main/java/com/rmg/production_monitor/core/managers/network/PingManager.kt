package com.rmg.production_monitor.core.managers.network

import com.rmg.production_monitor.core.data.Ping
import java.net.URL

interface PingManager {
   fun   doPing(url: URL): Ping
}