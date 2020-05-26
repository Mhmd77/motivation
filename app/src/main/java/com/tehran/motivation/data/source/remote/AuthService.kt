package com.tehran.motivation.data.source.remote


import com.tehran.motivation.data.*
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AuthApiService {
    @POST("api/signup")
    fun signUpAsync(@Body data: SignUpData): Deferred<ResponseAuth>

    @POST("api/login")
    fun loginAsync(@Body data: LoginData): Deferred<Response<User>>
}

object AuthApi {
    val api: AuthApiService = Network.retrofit.create(AuthApiService::class.java)
}

