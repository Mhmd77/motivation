package com.tehran.motivation.data.source.remote

import androidx.lifecycle.LiveData
import com.tehran.motivation.data.Category
import com.tehran.motivation.data.Response
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.SubCategory
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import timber.log.Timber
import java.lang.Exception



object RemoteCategoryDataSource  {
    interface CategoryApiService {
        @GET("api/getCategory")
        fun getMediaFromServerAsync(): Deferred<Response<List<Category>>>

        @GET("api/getAllSubcategories")
        fun getAllSubCategoriesAsync(): Deferred<Response<List<SubCategory>>>
    }

    private val api: CategoryApiService = Network.retrofit.create(CategoryApiService::class.java)

     suspend fun getCategories(): Result<List<Category>> {
        val deferred = api.getMediaFromServerAsync()
        val result = deferred.await()
        result.data?.let {
            return if (result.status == 200) {
                Result.Success(result.data)
            } else {
                Result.Error(Exception("code: ${result.status} message: ${result.message}"))
            }
        }
        return Result.Error(Exception("Data is null"))
    }
}