package com.rmg.production_monitor.models.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rmg.production_monitor.models.local.entity.WIPAnalyticsEntity

@Dao
interface WIPAnalyticsDao {
    @Query("SELECT * FROM wip")
    fun getWIPList():LiveData<WIPAnalyticsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWIPDAta(wipAnalyticsEntity: WIPAnalyticsEntity)
}