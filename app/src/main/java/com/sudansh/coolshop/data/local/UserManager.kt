package com.sudansh.coolshop.data.local

import com.sudansh.coolshop.util.toMd5

class UserManager(private val prefs: Prefs) {

    var email: String = ""
        set(value) {
            field = value
            prefs.save(PREFS_EMAIL, value)
        }
        get() = prefs.getString(PREFS_EMAIL)


    var token: String = ""
        set(value) {
            field = value
            prefs.save(PREFS_TOKEN, value)
        }
        get() = prefs.getString(PREFS_TOKEN)


    var password: String = ""
        set(value) {
            field = value
            prefs.save(PREFS_PASSWORD, value)
        }
        get() = prefs.getString(PREFS_PASSWORD)


    var userId: Int = 0
        set(value) {
            field = value
            prefs.save(PREFS_USER_ID, 0)
        }
        get() = prefs.getInt(PREFS_USER_ID)

    fun gravatarUrl(email: String): String {
        val codedEmail = email.toMd5()
        return "https://www.gravatar.com/avatar/$codedEmail"
    }


}