package com.tehran.motivation.util

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val NUM_MOTIVATION = 5
    const val NAME_SHARED_PREFERENCES = "Motivation Preferences"
    private const val pattern = "yyyy-MM-dd"
    val dateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
}