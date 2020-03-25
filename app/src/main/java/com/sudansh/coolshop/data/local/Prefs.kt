package com.sudansh.coolshop.data.local

import android.content.Context
import androidx.preference.PreferenceManager

class Prefs(context: Context) {
    val sharedPrefs by lazy { PreferenceManager.getDefaultSharedPreferences(context) }
    val editor by lazy { sharedPrefs.edit() }
    fun save(key: String, value: String) {
        editor.putString(key, value).commit()
    }

    fun save(key: String, value: Int) {
        editor.putInt(key, value).commit()
    }

    fun getString(key: String): String {
        return sharedPrefs.getString(key, "").orEmpty()
    }

    fun getInt(key: String): Int {
        return sharedPrefs.getInt(key, 0)
    }
}