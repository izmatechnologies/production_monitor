package com.rmg.production_monitor.models.local.dao



import androidx.room.*
import com.rmg.production_monitor.models.local.entity.QcOperationEntity


@Dao
interface QcOperationDao {


    @Query("SELECT * FROM qc_operation ")
    fun getQcOperationList(): List<QcOperationEntity>
    @Query("SELECT COUNT(*) FROM qc_operation ")
    fun getTotalQcOperationRowCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQcOperation(model: QcOperationEntity):Long


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateQcOperation(model: QcOperationEntity)

    @Delete
    fun deleteQcOperation(model: QcOperationEntity)
    @Query("DELETE FROM qc_operation")
     fun deleteAllQcOperation()
}