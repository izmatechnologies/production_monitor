package com.faisal.quc.models.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.google.gson.annotations.SerializedName


@Keep
@Entity(tableName = "qc_operation")
data class QcOperationEntity (
    @PrimaryKey
    @SerializedName("Id")
    val id: Int,

    @SerializedName("OperationName")
    val operationName: String,

    @SerializedName("OperationDescription")
    val operationDescription: String
)


/**
 *  A Extension function that convert response to entity
 */
//fun QCOperationModel.toQCOperationEntity(
//
//):QcOperationEntity{
//    return QcOperationEntity(
//        id = this.id,
//        operationName = this.operationName,
//        operationDescription = this.operationDescription
//    )
//}
