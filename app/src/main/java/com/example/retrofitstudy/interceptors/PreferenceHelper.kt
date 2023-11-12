package com.example.retrofitstudy.interceptors

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {
    companion object {
        val PREF_NAME = "PREF_COOKIES"

        fun getCookies(context: Context): MutableSet<String> {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            // val set : java.util.Set<String> = java.util.HashSet<String>() as Set<String>
            return pref.getStringSet("cookies", mutableSetOf()) as MutableSet<String>
        }

        fun setCookies(context: Context, cookies: MutableSet<String>) {
            val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putStringSet("cookies", cookies)
            editor.apply()
        }

        fun removeCookies(context: Context) {
            val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
        }
    }
}