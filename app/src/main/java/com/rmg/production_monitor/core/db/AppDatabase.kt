package com.rmg.production_monitor.core.db

import android.content.Context
import androidx.room.*
import com.rmg.production_monitor.core.Config



@Database(
    entities = [
        QcOperationEntity::class,
        QcIssueEntity::class,

    ],
    version = Config.Storage.APPLICATION_DATABASE_VERSION,
    exportSchema = false
)
//@TypeConverters(StringArrayTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getQcOperationDao(): QcOperationDao
    abstract fun getQcIssueDao(): QcIssueDao



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