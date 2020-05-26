package com.tehran.motivation.data.source.remote

import androidx.lifecycle.LiveData
import com.tehran.motivation.data.Category
import com.tehran.motivation.data.Response
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.SubCategory
import com.tehran.motivation.data.source.CategoryDataSource
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import timber.log.Timber
import java.lang.Exception

interface CategoryApiService {
    @GET("api/getCategory")
    fun getMediaFromServerAsync(): Deferred<Response<List<Category>>>

    @GET("api/getAllSubcategories")
    fun getAllSubCategoriesAsync(): Deferred<Response<List<SubCategory>>>
}

object RemoteCategoryDataSource : CategoryDataSource {
    private val api: CategoryApiService = Network.retrofit.create(CategoryApiService::class.java)

    override fun observeCategories(): LiveData<Result<List<Category>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategories(): Result<List<Category>> {
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

    override suspend fun refreshCategories() {
        TODO("Not yet implemented")
    }

    override fun observeCategory(categoryId: Long): LiveData<Result<Category>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategory(categoryId: Long): Result<Category> {
        TODO("Not yet implemented")
    }

    override suspend fun saveCategories(categories: List<Category>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllCategories() {
        TODO("Not yet implemented")
    }

}