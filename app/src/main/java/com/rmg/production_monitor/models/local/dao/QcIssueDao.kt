package com.faisal.quc.models.local.dao



import androidx.room.*
import com.faisal.quc.models.local.entity.QcIssueEntity


import kotlinx.coroutines.flow.Flow


@Dao
interface QcIssueDao {


    @Query("SELECT * FROM qc_issue ")
    fun getAllQcIssuesList(): List<QcIssueEntity>

    @Query("SELECT * FROM qc_issue WHERE sewingOperationID = :operationID ")
    fun getQcIssuesList(operationID:Int): List<QcIssueEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQcIssue(model: QcIssueEntity):Long



//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun updateQcOperation(model: QcIssueEntity)

    @Delete
    fun deleteQcIssue(model: QcIssueEntity)


    @Query("DELETE FROM qc_issue")
     fun deleteAllQcIssue()
}