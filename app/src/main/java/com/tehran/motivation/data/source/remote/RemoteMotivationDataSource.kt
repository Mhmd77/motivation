package com.tehran.motivation.data.source.remote

import androidx.lifecycle.LiveData
import com.tehran.motivation.data.Motivation
import com.tehran.motivation.data.Response
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.source.MotivationDataSource
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.Exception


object RemoteMotivationDataSource : MotivationDataSource {
    interface MotivationApiService {
        @GET("api/getMotivations/{subId}")
        fun getMotivationsAsync(@Path("subId") subCategoryId: Long): Deferred<Response<List<Motivation>>>

    }

    private val api: MotivationApiService =
        Network.retrofit.create(MotivationApiService::class.java)

    override suspend fun getMotivations(subId: Long): Result<List<Motivation>> {
        val deferred = api.getMotivationsAsync(subId)
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

    override suspend fun getTodayMotivations(): Result<List<Motivation>> {
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