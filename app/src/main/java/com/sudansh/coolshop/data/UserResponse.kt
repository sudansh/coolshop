package com.sudansh.coolshop.data

import com.google.gson.annotations.SerializedName

class UserResponse(
    @SerializedName("email") val email: String,
    @SerializedName("avatar_url") val avatarUrl: String
)