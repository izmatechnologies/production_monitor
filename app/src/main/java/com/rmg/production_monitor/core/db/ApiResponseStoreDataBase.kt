package com.rmg.production_monitor.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rmg.production_monitor.R
import com.rmg.production_monitor.core.Converters
import com.rmg.production_monitor.models.local.dao.CumulativeDashBoardDao
import com.rmg.production_monitor.models.local.dao.HeatMapDao
import com.rmg.production_monitor.models.local.dao.PCBDashboardDetailsDao
import com.rmg.production_monitor.models.local.dao.WIPAnalyticsDao
import com.rmg.production_monitor.models.local.entity.CumulativeDashBoardEntity
import com.rmg.production_monitor.models.local.entity.HeatMapEntity
import com.rmg.production_monitor.models.local.entity.PCBDashBoardDetailsEntity
import com.rmg.production_monitor.models.local.entity.WIPAnalyticsEntity

@Database(
    entities = [
        HeatMapEntity::class,
        CumulativeDashBoardEntity::class,
        WIPAnalyticsEntity::class,
    PCBDashBoardDetailsEntity::class
    ], version = 3, exportSchema = false
)

@TypeConverters(Converters::class)
abstract class ApiResponseStoreDataBase : RoomDatabase() {

    abstract fun getHeatMapDao(): HeatMapDao
    abstract fun getCumulativeDashBoardDao(): CumulativeDashBoardDao
    abstract fun getWIPAnalyticsDao(): WIPAnalyticsDao
    abstract fun getPCBDashboardDetailsDao(): PCBDashboardDetailsDao

    companion object {
        @Volatile
        private var INSTAMCE: ApiResponseStoreDataBase? = null

        fun getInstance(context: Context): ApiResponseStoreDataBase {
            synchronized(this) {
                var instance: ApiResponseStoreDataBase? = INSTAMCE
                if (instance == null) {
                    instance = Room
                        .databaseBuilder(
                            context.applicationContext,
                            ApiResponseStoreDataBase::class.java,
                            "${context.getString(R.string.app_name)} database"
                        )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTAMCE = instance
                }
                return instance
            }
        }
    }
}