package com.rmg.production_monitor.service.broadCastCallReceiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock

import androidx.lifecycle.LifecycleService

class ApiSchedulerService:LifecycleService() {
    fun heatMapScheduleApiCall(lineId: Int){
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, HeatmapCallReceiver::class.java).apply {
            action = "heatmap_api"
            putExtra("LINE_ID", lineId)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set repeating alarm for API 1 every 3 minutes
        alarmManager.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(), // First trigger
            3 * 60 * 1000, // Repeat every 3 minutes
            pendingIntent
        )
    }
}