package com.tehran.motivation.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id") val id: Long,
    @Json(name = "email") val email: String,
    @Json(name = "name") val name: String,
    @Json(name = "motivation_count") val count: Int
)

@JsonClass(generateAdapter = true)
data class SignUpData(
    val name: String,
    val email: String,
    val password: String
)

@JsonClass(generateAdapter = true)
data class LoginData(
    val email: String,
    val password: String
)
@JsonClass(generateAdapter = true)
data class ResponseAuth(
    @Json(name = "status") val status: Int,
    @Json(name = "data") val user: User?,
    @Json(name = "message") val message: String
)