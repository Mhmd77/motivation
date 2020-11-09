package com.tehran.motivation.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tehran.motivation.ServiceLocator
import com.tehran.motivation.data.source.CategoryRepository
import timber.log.Timber

class CategoryWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {


    companion object {
        const val WORK_NAME = "com.tehran.motivation.work.CategoryWorker"
        const val WORK_TAG = "FetchCategoryJob"
    }

    override suspend fun doWork(): Result {
        val categoryRepository = ServiceLocator.provideCategoryRepository(applicationContext)
        return getCategoriesFromRemote(categoryRepository)
    }


    private suspend fun getCategoriesFromRemote(repository: CategoryRepository): Result {
        return when (val result = repository.refreshCategories()) {
            is com.tehran.motivation.data.Result.Success -> {
                Timber.d("Successfull Category Update")
                Result.success()
            }
            is com.tehran.motivation.data.Result.Error -> {
                Timber.d("Retry: ${result.exception.message}")
                Result.retry()
            }
            else -> {
                Timber.d("Failed")
                Result.failure()
            }
        }
    }
}