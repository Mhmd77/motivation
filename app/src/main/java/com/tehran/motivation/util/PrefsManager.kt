package com.tehran.motivation.util

import android.content.Context
import android.content.SharedPreferences

class PrefsManager(context: Context) {
    companion object {
        private const val MOTIVATION_KEY = "Motivation Number"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun updateMotivationNumber() {
        synchronized(this) {
            val number = getMotivationNumber()
            val editor = sharedPreferences.edit()
            editor.putInt(MOTIVATION_KEY, number + 1)
            editor.apply()
        }
    }

    fun resetMotivationNumber() {
        synchronized(this) {
            val editor = sharedPreferences.edit()
            editor.putInt(MOTIVATION_KEY, 0)
            editor.apply()
        }
    }

    fun getMotivationNumber(): Int {
        synchronized(this) {
            return sharedPreferences.getInt(MOTIVATION_KEY, 0)
        }
    }


}