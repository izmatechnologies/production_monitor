package com.rmg.production_monitor.core

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.rmg.production_monitor.models.remote.quality.QualityPayload

class Converters {

    @TypeConverter
    fun fromQualityPayload(payload: QualityPayload?): String? {
        return if (payload == null) null else Gson().toJson(payload)
    }

    @TypeConverter
    fun toQualityPayload(payloadString: String?): QualityPayload? {
        return if (payloadString == null) null else Gson().fromJson(payloadString, QualityPayload::class.java)
    }
}