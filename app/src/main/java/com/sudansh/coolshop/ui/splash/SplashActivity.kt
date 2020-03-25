package com.sudansh.coolshop.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sudansh.coolshop.data.local.UserManager
import com.sudansh.coolshop.ui.detail.MainActivity
import com.sudansh.coolshop.ui.login.LoginActivity
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val userManager by inject<UserManager>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!userManager.token.isEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}
