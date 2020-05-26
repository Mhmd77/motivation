package com.tehran.motivation.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Response<out T>(
     val data: T,
     val message: String,
     val status: Int
)