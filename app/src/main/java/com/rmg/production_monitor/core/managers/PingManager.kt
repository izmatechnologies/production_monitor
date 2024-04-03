package com.rmg.production_monitor.core.managers

import com.rmg.production_monitor.core.managers.Ping
import java.net.URL

interface PingManager {
   fun   doPing(url: URL): Ping
}