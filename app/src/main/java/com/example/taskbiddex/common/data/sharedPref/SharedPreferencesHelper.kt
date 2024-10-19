package com.abdallah.ecommerce.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.taskbiddex.MyApplication

object SharedPreferencesHelper {
    private val preferences: SharedPreferences =
        MyApplication.myAppContext.getSharedPreferences("biddexTask", Context.MODE_PRIVATE)

    fun addBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun addString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun getString(key: String): String {
        return preferences.getString(key, "") ?: ""
    }

    fun contain(key: String): Boolean {
        return preferences.contains(key)
    }

}