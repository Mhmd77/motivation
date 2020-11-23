package com.tehran.motivation.data.source.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tehran.motivation.MyApplication
import com.tehran.motivation.util.AddCookiesInterceptor
import com.tehran.motivation.util.ReceivedCookiesInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object Network {
    private const val BASE_URL = "http://49.12.56.172:80"

    private val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(AddCookiesInterceptor(MyApplication.applicationContext()))
        .addInterceptor(ReceivedCookiesInterceptor(MyApplication.applicationContext()))
        .also {
            val interceptor = HttpLoggingInterceptor().also { int ->
                int.level = HttpLoggingInterceptor.Level.BODY
            }
            it.addInterceptor(interceptor)
        }

    private val client: OkHttpClient = builder.build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(client)
        .build()
}