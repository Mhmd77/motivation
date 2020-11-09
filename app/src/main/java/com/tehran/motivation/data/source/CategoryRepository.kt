package com.tehran.motivation.data.source

import androidx.lifecycle.LiveData
import com.tehran.motivation.data.Category
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.source.local.category.LocalCategoryDataSource
import com.tehran.motivation.data.source.remote.RemoteCategoryDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CategoryRepository(
    private val remoteDataSource: RemoteCategoryDataSource,
    private val localDataSource: LocalCategoryDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun observeCategories(): LiveData<Result<List<Category>>> {
        return localDataSource.observeCategories()
    }

    suspend fun getCategories(forceUpdate: Boolean): Result<List<Category>> {
        if (forceUpdate) {
            try {
                updateCategoriesFromRemoteDataSource()
            } catch (ex: Exception) {
                return Result.Error(ex)
            }
        }
        return localDataSource.getCategories()
    }

    suspend fun refreshCategories(): Result<Any> {
        try {
            updateCategoriesFromRemoteDataSource()
        } catch (e: Exception) {
            return Result.Error(e)
        }
        return Result.Success(Any())
    }


    private suspend fun updateCategoriesFromRemoteDataSource() {
        val remoteCategories = remoteDataSource.getCategories()
        if (remoteCategories is Result.Success) {
            localDataSource.deleteAllCategories()
            localDataSource.saveCategories(remoteCategories.data)
        } else if (remoteCategories is Result.Error) {
            throw remoteCategories.exception
        }
    }
}