package com.rmg.production_monitor.core.extention

import android.content.Context
import android.widget.Toast



fun String.toast(context:Context = ProductionMonitorApplication.getContext()) {
            Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }