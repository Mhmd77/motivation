package com.tehran.motivation.data.source.remote

import androidx.lifecycle.LiveData
import com.tehran.motivation.data.Motivation
import com.tehran.motivation.data.Response
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.source.MotivationDataSource
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import timber.log.Timber
import java.lang.Exception


object RemoteMotivationDataSource : MotivationDataSource {
    interface MotivationApiService {
        @GET("api/getMotivations/{subId}")
        fun getMotivationsAsync(@Path("subId") subCategoryId: Long): Deferred<Response<List<Motivation>>>

        @GET("api/getBookmark")
        fun getFavoritesAsync(): Deferred<Response<List<Motivation>>>

        @GET("/api/markMotivation/{Id}")
        fun addToFavorites(@Path("Id") id: Long): Deferred<Response<Any>>

        @GET("/api/unmarkMotivation/{Id}")
        fun removeFromFavorites(@Path("Id") id: Long): Deferred<Response<Any >>
    }

    private val api: MotivationApiService =
        Network.retrofit.create(MotivationApiService::class.java)

    override suspend fun getMotivations(subId: Long): Result<List<Motivation>> {
        return getResultFromDeferred(api.getMotivationsAsync(subId))
    }

    override suspend fun getFavorites(): Result<List<Motivation>> {
        return getResultFromDeferred(api.getFavoritesAsync())
    }

    override suspend fun addToFavorites(id: Long): Result<Boolean> {
        return try {
            val deferred = api.addToFavorites(id)
            val result = deferred.await()
            if (result.status == 200) {
                Result.Success(true)
            } else {
                Result.Error(Exception(result.message))
            }
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(Exception(e.message))
        }
    }

    override suspend fun removeFromFav(id: Long): Result<Boolean> {
        return try {
            val deferred = api.removeFromFavorites(id)
            val result = deferred.await()
            if (result.status == 200) {
                Result.Success(true)
            } else {
                Result.Error(Exception(result.message))
            }
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(Exception(e.message))
        }
    }

    private suspend fun getResultFromDeferred(deferred: Deferred<Response<List<Motivation>>>): Result<List<Motivation>> {
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

    override fun observeMotivation(motivationId: Long): LiveData<Result<Motivation>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMotivation(): Result<Motivation> {
        TODO("Not yet implemented")
    }

    override suspend fun getTodayMotivations(from: Int, n: Int): Result<List<Motivation>> {
        TODO("Not yet implemented")
    }

    override fun observeTodayMotivations(from: Int, n: Int): LiveData<List<Motivation>?> {
        TODO("Not yet implemented")
    }

    override fun observeSavedMotivations(): LiveData<Result<List<Motivation>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSavedMotivations(): Result<List<Motivation>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveMotivation(motivation: Motivation) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllSavedMotivations() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSavedMotivation(motivationId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllMotivations() {
        TODO("Not yet implemented")
    }

    override suspend fun insertMotivations(motivations: List<Motivation>) {
        TODO("Not yet implemented")
    }

}