package com.tehran.motivation.util

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val NAME_SHARED_PREFERENCES = "Motivation Preferences"
    private const val pattern = "yyyy-MM-dd'T'HH:mm:ss"
    val dateFormat = SimpleDateFormat(pattern).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
}