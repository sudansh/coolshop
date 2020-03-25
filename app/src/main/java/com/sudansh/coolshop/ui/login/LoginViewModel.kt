package com.sudansh.coolshop.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudansh.coolshop.R
import com.sudansh.coolshop.data.LoginResponse
import com.sudansh.coolshop.data.repository.UserRepository
import com.sudansh.coolshop.data.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: UserRepository) : ViewModel() {

    val email = MutableLiveData<String>()
    val emailError = MutableLiveData<Int>()
    val password = MutableLiveData<String>()
    val passwordError = MutableLiveData<Int>()
    private val _loginResult = MutableLiveData<State<LoginResponse>>()
    val loginResult: LiveData<State<LoginResponse>> = _loginResult

    fun login() {
        if (isFormValid(email.value, password.value)) {
            viewModelScope.launch {
                loginRepository.login(email.value!!, password.value!!)
                    .collect { _loginResult.value = it }
            }
        }
    }

    private fun isFormValid(username: String?, password: String?): Boolean {
        emailError.value = null
        passwordError.value = null
        var isValid = true
        if (!isUserNameValid(username)) {
            emailError.value = R.string.invalid_username
            isValid = false
        } else if (!isPasswordValid(password)) {
            passwordError.value = R.string.invalid_password
            isValid = false
        }
        return isValid
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username.orEmpty()).matches()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String?): Boolean {
        return password.orEmpty().length > 5
    }
}
