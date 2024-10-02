package com.rmg.production_monitor.models.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
//import com.faisal.quc.models.remote.SewingQcIssueModel
import com.google.gson.annotations.SerializedName
import com.rmg.production_monitor.models.local.entity.QcOperationEntity


@Keep
@Entity(tableName = "qc_issue",
    foreignKeys = arrayOf(
        ForeignKey(entity = QcOperationEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("sewingOperationID"),
        onDelete = ForeignKey.CASCADE)
    ))
data class QcIssueEntity (
    @PrimaryKey
    @SerializedName("Id")
    val id: Int,

    @SerializedName("SewingOperationId")
    val sewingOperationID: Int,

    @SerializedName("IssueName")
    val issueName: String,

    @SerializedName("IssueDescription")
    val issueDescription: String?
)


/**
 *  A Extension function that convert response to entity
 */
//fun SewingQcIssueModel.toQcIssueEntity(
//
//):QcIssueEntity{
//    return QcIssueEntity(
//        id = this.id,
//        sewingOperationID = this.sewingOperationID,
//        issueName = this.issueName,
//        issueDescription= this.issueDescription
//    )
//}
