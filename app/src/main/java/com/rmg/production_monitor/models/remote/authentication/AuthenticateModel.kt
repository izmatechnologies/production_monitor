package com.rmg.production_monitor.models.remote.authentication
import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName



//
//@Keep
//data class AuthenticateModel(
//    @SerializedName("Success")
//    val success: Boolean? = null,
//    @SerializedName("Message")
//    val message: String? = null,
//    @SerializedName("Payload")
//    val authenticatePayload: AuthenticatePayload? = null
//)
//
//@Keep
//data class AuthenticatePayload(
//    @SerializedName("UserName")
//    val userName: String? = null,
//    @SerializedName("UserTypeName")
//    val userTypeName: String? = null,
//    @SerializedName("AccessToken")
//    val accessToken: String? = null
//)
@Keep
data class AuthenticateModel(
    @SerializedName("Message")
    val message: String? =null,
    @SerializedName("Payload")
    val authenticatePayload: AuthenticatePayload? = null,
    @SerializedName("Success")
    val success: Boolean? = false
)

@Keep
data class AuthenticatePayload(
    @SerializedName("AccessToken")
    val accessToken: String? =null,
    @SerializedName("UserLines")
    val userLines: List<UserLine>,
    @SerializedName("UserName")
    val userName: String? = null,
    @SerializedName("UserPlantUnits")
    val userPlantUnits: List<UserPlantUnit>,
    @SerializedName("UserPlants")
    val userPlants: List<UserPlant>,
    @SerializedName("UserTypeName")
    val userTypeName: String? = null
)

@Keep
data class UserLine(
    @SerializedName("LineId")
    val lineId: Int? = 0,
    @SerializedName("LineName")
    val lineName: String? =null,
    @SerializedName("PlantId")
    val plantId: Int? = 0,
    @SerializedName("PlantUnitId")
    val plantUnitId: Int? = 0
){
    override fun toString(): String {
        return lineName.toString()
    }
}

@Keep
data class UserPlantUnit(
    @SerializedName("PlantId")
    val plantId: Int? = 0,
    @SerializedName("PlantUnitId")
    val plantUnitId: Int? = 0,
    @SerializedName("PlantUnitName")
    val plantUnitName: String? = null
)
{
    override fun toString(): String {
        return plantUnitName.toString()
    }
}

@Keep
data class UserPlant(
    @SerializedName("PlantId")
    val plantId: Int? = 0,
    @SerializedName("PlantName")
    val plantName: String? = null
){
    override fun toString(): String {
        return plantName.toString()
    }
}