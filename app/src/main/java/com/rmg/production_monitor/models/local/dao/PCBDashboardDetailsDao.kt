package com.rmg.production_monitor.models.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rmg.production_monitor.models.local.entity.PCBDashBoardDetailsEntity

@Dao
interface PCBDashboardDetailsDao {
    @Query("SELECT * FROM pcb")
    fun getPCBDashboardDetails():LiveData<PCBDashBoardDetailsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPCBDashBoardDetailsData(pcbDashBoardDetailsEntity: PCBDashBoardDetailsEntity)
}