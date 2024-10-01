package com.rmg.production_monitor.models.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rmg.production_monitor.models.local.entity.CumulativeDashBoardEntity

@Dao
interface CumulativeDashBoardDao {
    @Query("SELECT * FROM cumulative_dashboard")
    fun getDashBoardSummary():LiveData<CumulativeDashBoardEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDashBoardData(cumulativeDashBoardEntity: CumulativeDashBoardEntity)
}