package com.sudansh.coolshop.data.network

import com.sudansh.coolshop.data.AvatarResponse
import com.sudansh.coolshop.data.LoginResponse
import com.sudansh.coolshop.data.UserResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("/sessions/new")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): LoginResponse

    @GET("/users")
    suspend fun userDetail(@Query("userdid") userId: Int): UserResponse

    @POST("/users/{userId}/avatar")
    suspend fun uploadAvatar(
        @Path("userId") userId: Int,
        @Query("avatar") avatarImage: String
    ): AvatarResponse

}