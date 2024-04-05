package com.rmg.production_monitor.core.data



data class Ping (
    var net : String = "NO_CONNECTION",
    var host : String = "",
    var ip: String  = "",
    var dns : Long = Long.MAX_VALUE,
    var cnt : Long= Long.MAX_VALUE
)

