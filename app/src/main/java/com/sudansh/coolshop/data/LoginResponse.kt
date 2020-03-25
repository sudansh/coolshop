package com.sudansh.coolshop.data

import com.google.gson.annotations.SerializedName

class LoginResponse(
    @SerializedName("userid") val userId: Int,
    @SerializedName("token") val token: String
)