package com.rmg.production_monitor.models.remote.authentication
import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName




@Keep
data class AuthenticateModel(
    @SerializedName("Success")
    val success: Boolean? = null,
    @SerializedName("Message")
    val message: String? = null,
    @SerializedName("Payload")
    val authenticatePayload: AuthenticatePayload? = null
)

@Keep
data class AuthenticatePayload(
    @SerializedName("UserName")
    val userName: String? = null,
    @SerializedName("UserTypeName")
    val userTypeName: String? = null,
    @SerializedName("AccessToken")
    val accessToken: String? = null
)
