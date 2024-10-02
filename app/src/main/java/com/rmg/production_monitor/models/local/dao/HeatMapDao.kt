package com.rmg.production_monitor.models.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rmg.production_monitor.models.local.entity.HeatMapEntity

@Dao
interface HeatMapDao {
    @Query("SELECT * FROM heatmap ")
    fun getHeatMapList(): LiveData<HeatMapEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeatMapData(heatMapEntity: HeatMapEntity)

}