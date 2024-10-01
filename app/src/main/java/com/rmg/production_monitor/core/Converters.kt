package com.rmg.production_monitor.core

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.rmg.production_monitor.models.remote.cumulativeDashboardDetail.CumulativeDashboardDetailPayload
import com.rmg.production_monitor.models.remote.cumulativeDashboardSummary.CumulativeDashboardSummaryPayload
import com.rmg.production_monitor.models.remote.dasboard.Payload
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


    @TypeConverter
    fun fromWIPPayload(payload: Payload?): String? {
        return if (payload == null) null else Gson().toJson(payload)
    }

    @TypeConverter
    fun toWIPPayload(payloadString: String?): Payload? {
        return if (payloadString == null) null else Gson().fromJson(payloadString, Payload::class.java)
    }


    @TypeConverter
    fun fromPCBPayload(payload: CumulativeDashboardDetailPayload?): String? {
        return if (payload == null) null else Gson().toJson(payload)
    }

    @TypeConverter
    fun toPCBPayload(payloadString: String?): CumulativeDashboardDetailPayload? {
        return if (payloadString == null) null else Gson().fromJson(payloadString, CumulativeDashboardDetailPayload::class.java)
    }

    @TypeConverter
    fun fromDashBoardSummaryPayload(payload: CumulativeDashboardSummaryPayload?): String? {
        return if (payload == null) null else Gson().toJson(payload)
    }

    @TypeConverter
    fun toDashBoardSummaryPayload(payloadString: String?): CumulativeDashboardSummaryPayload? {
        return if (payloadString == null) null else Gson().fromJson(payloadString, CumulativeDashboardSummaryPayload::class.java)
    }


}