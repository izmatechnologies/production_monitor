package com.faisal.quc.models.remote.authentication

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class AuthenticationRequest (
    @SerializedName("UserName")
    val userName: String,

    @SerializedName("Password")
    val password: String
)
