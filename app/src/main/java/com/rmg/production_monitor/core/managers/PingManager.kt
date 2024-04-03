package com.faisal.quc.core.managers

import android.content.Context
import java.net.URL

interface PingManager {
   fun   doPing(url: URL):Ping
}