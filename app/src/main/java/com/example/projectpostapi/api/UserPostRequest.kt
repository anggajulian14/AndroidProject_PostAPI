package com.example.projectpostapi.api

import com.google.gson.annotations.SerializedName

data class UserPostRequest(
    @SerializedName ("email") val email: String,
    @SerializedName ("password") val password: String
)
