package com.faisal.quc.models.local.dao



import androidx.room.*
import com.faisal.quc.models.local.entity.QcOperationEntity

import kotlinx.coroutines.flow.Flow


@Dao
interface QcOperationDao {


    @Query("SELECT * FROM qc_operation ")
    fun getQcOperationList(): List<QcOperationEntity>
    @Query("SELECT COUNT(*) FROM qc_operation ")
    fun getTotalQcOperationRowCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQcOperation(model: QcOperationEntity):Long

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertQcOperationsList(products: List<QcOperationEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateQcOperation(model: QcOperationEntity)

    @Delete
    fun deleteQcOperation(model: QcOperationEntity)
    @Query("DELETE FROM qc_operation")
     fun deleteAllQcOperation()
}