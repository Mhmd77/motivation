package com.tehran.motivation.data

import com.squareup.moshi.Json

data class User(
    @Json(name = "id") val id: Long,
    @Json(name = "email") val email: String,
    @Json(name = "name") val name: String,
    @Json(name = "motivation_count") val count: Int
)

data class SignUpData(
    private val name: String,
    private val email: String,
    private val password: String
)

data class LoginData(
    private val email: String,
    private val password: String
)

data class ResponseAuth(
    @Json(name = "status") val status: Int,
    @Json(name = "data") val user: User?,
    @Json(name = "message") val message: String
)