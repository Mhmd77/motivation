package com.tehran.motivation.data.source.remote


import com.tehran.motivation.data.LoginData
import com.tehran.motivation.data.ResponseAuth
import com.tehran.motivation.data.SignUpData
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AuthApiService {
    @POST("api/signup")
    fun signUpAsync(@Body data: SignUpData): Deferred<ResponseAuth>

    @POST("api/login")
    fun loginAsync(@Body data: LoginData): Deferred<ResponseAuth>

    @GET("api/getMedia/1")
    fun mediaAsync():Deferred<ResponseBody>
}

object AuthApi {
    val api: AuthApiService = Network.retrofit.create(AuthApiService::class.java)
}

