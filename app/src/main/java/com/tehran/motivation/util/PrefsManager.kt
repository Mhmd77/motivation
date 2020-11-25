package com.tehran.motivation.util

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.tehran.motivation.data.SubCategory
import java.util.*

class PrefsManager(context: Context) {
    companion object {
        private const val MOTIVATION_KEY = "Motivation Number"
        private const val SUBCATEGORY_KEY = "Subcategory Id"
        private const val DATE_KEY = "Date"
    }

    private val gson = Gson()

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

    fun selectSubCategory(item: SubCategory) {
        synchronized(this) {
            val editor = sharedPreferences.edit()
            val json: String = gson.toJson(item)
            editor.putString(SUBCATEGORY_KEY, json)
            editor.apply()
        }
    }

    fun getSelectedSubCategory(): SubCategory? {
        val json = sharedPreferences.getString(SUBCATEGORY_KEY, null)
        return gson.fromJson(json ?: return null, SubCategory::class.java)
    }

    fun updateMotivationDate(date: Date) {
        synchronized(this) {
            val dateString = Constants.dateFormat.format(date)
            val editor = sharedPreferences.edit()
            editor.putString(DATE_KEY, dateString)
            editor.apply()
        }
    }

    fun getUpdateMotivationDate(): Date? {
        val date = sharedPreferences.getString(DATE_KEY, null)
        return Constants.dateFormat.parse(date ?: return null)
    }
}