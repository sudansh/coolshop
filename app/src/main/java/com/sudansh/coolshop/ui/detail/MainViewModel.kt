package com.sudansh.coolshop.ui.detail

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudansh.coolshop.data.State
import com.sudansh.coolshop.data.local.UserManager
import com.sudansh.coolshop.data.repository.UserRepository
import com.sudansh.coolshop.util.applyGscale
import com.sudansh.coolshop.util.getCompressedBitmapData
import com.sudansh.coolshop.util.toBase64
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(
    private val user: UserRepository,
    private val userManager: UserManager
) :
    ViewModel() {

    val email by lazy { userManager.email }
    val password by lazy { userManager.password }
    val avatarUrl = MutableLiveData<String>()

    fun getDetail() {
        viewModelScope.launch {
            user.userDetail(userManager.userId).collect {
                //Check if profile pic exists else use gravatar url
                val profilePicUrl = if (it is State.Success) {
                    if (!it.data.avatarUrl.isNullOrEmpty()) {
                        it.data.avatarUrl
                    } else {
                        userManager.gravatarUrl(email)
                    }
                } else ""

                avatarUrl.postValue(profilePicUrl)
            }
        }
    }

    fun uploadAvatar(imageBitmap: Bitmap) {
        viewModelScope.launch {
            val filteredBitmap = applyGscale(imageBitmap) ?: return@launch
            val base64 = toBase64(filteredBitmap)
            user.uploadAvatar(userManager.userId, base64).collect {
                val profilePicUrl = if (it is State.Success) {
                    if (it.data.avatarUrl.isNotEmpty()) {
                        it.data.avatarUrl
                    } else {
                        userManager.gravatarUrl(email)
                    }
                } else ""

                avatarUrl.postValue(profilePicUrl)
            }
        }
    }

    fun compressImageAndUpload(imageBitmap: Bitmap) {
        viewModelScope.launch {
            val compressImage = getCompressedBitmapData(imageBitmap, 1000) ?: return@launch
            uploadAvatar(compressImage)
        }
    }
}