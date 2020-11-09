package com.tehran.motivation.data.source.remote

import com.tehran.motivation.data.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import java.lang.Exception


object RemoteMediaDataSource {
    interface MediaApiService {
        @GET("api/getMedia")
        fun getMediaAsync(): Deferred<Response<MediaHolder>>
    }

    private val api: MediaApiService =
        Network.retrofit.create(MediaApiService::class.java)

    suspend fun getMedias(): Result<MediaHolder> {
        val deferred = api.getMediaAsync()
        val result = deferred.await()
        result.data?.let {
            return if (result.status == 200) {
                Result.Success(data = it)
            } else {
                Result.Error(Exception(result.message))
            }
        }
        return Result.Error(Exception("Data is null"))
    }
}