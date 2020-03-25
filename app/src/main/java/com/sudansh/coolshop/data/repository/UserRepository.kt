package com.sudansh.coolshop.data.repository

import com.sudansh.coolshop.data.AvatarResponse
import com.sudansh.coolshop.data.LoginResponse
import com.sudansh.coolshop.data.State
import com.sudansh.coolshop.data.UserResponse
import com.sudansh.coolshop.data.local.UserManager
import com.sudansh.coolshop.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


@ExperimentalCoroutinesApi
class UserRepository(
    private val userManager: UserManager,
    private val apiService: ApiService
) {

    fun login(email: String, password: String) = flow<State<LoginResponse>> {
        emit(State.loading())
        try {
            val response = apiService.login(email, password)
            userManager.email = email
            userManager.password = password
            userManager.token = response.token
            userManager.userId = response.userId
            emit(State.success(response))
        } catch (e: Exception) {
            emit(State.error("Network error!"))
        }
    }.flowOn(Dispatchers.IO)

    fun userDetail(userId: Int) = flow<State<UserResponse>> {
        emit(State.loading())
        try {
            emit(State.success(apiService.userDetail(userId)))
        } catch (e: Exception) {
            emit(State.error("Network error!"))
        }
    }.flowOn(Dispatchers.IO)

    fun uploadAvatar(userId: Int, image: String) = flow<State<AvatarResponse>> {
        emit(State.loading())
        try {
            emit(State.success(apiService.uploadAvatar(userId, image)))
        } catch (e: Exception) {
            emit(State.error("Network error!"))
        }
    }.flowOn(Dispatchers.IO)

}