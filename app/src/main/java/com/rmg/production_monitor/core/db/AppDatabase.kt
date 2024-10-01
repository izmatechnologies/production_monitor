package com.rmg.production_monitor.core.db

import android.content.Context
import androidx.room.*
import com.rmg.production_monitor.models.local.dao.QcOperationDao
import com.rmg.production_monitor.models.local.entity.QcOperationEntity
import com.rmg.production_monitor.core.Config
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
    entities = [QcOperationEntity::class,
        HeatMapEntity::class,
        CumulativeDashBoardEntity::class,
        WIPAnalyticsEntity::class,
        PCBDashBoardDetailsEntity::class],
    version = Config.Storage.APPLICATION_DATABASE_VERSION,
    exportSchema = false
)
//@TypeConverters(StringArrayTypeConverter::class)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getQcOperationDao(): QcOperationDao
    abstract fun getHeatMapDao(): HeatMapDao
    abstract fun getCumulativeDashBoardDao(): CumulativeDashBoardDao
    abstract fun getWIPAnalyticsDao(): WIPAnalyticsDao
    abstract fun getPCBDashboardDetailsDao(): PCBDashboardDetailsDao


    companion object {
        private var DB_INSTANCE: AppDatabase? = null

        fun getAppDBInstance(context: Context): AppDatabase {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    Config.Storage.APPLICATION_DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return DB_INSTANCE!!
        }
    }

}