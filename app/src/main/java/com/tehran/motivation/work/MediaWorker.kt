package com.tehran.motivation.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tehran.motivation.ServiceLocator
import com.tehran.motivation.data.source.CategoryRepository
import com.tehran.motivation.data.source.MediaRepository
import timber.log.Timber

class MediaWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {


    companion object {
        const val WORK_NAME = "com.tehran.motivation.work.MediaWorker"
        const val WORK_TAG = "FetchMediaJob"
    }

    override suspend fun doWork(): Result {
        val categoryRepository = ServiceLocator.provideMediaRepository(applicationContext)
        return getMediasFromRemote(categoryRepository)
    }


    private suspend fun getMediasFromRemote(repository: MediaRepository): Result {
        return when (val result = repository.refreshMedia()) {
            is com.tehran.motivation.data.Result.Success -> {
                Timber.d("Successfull Media Update")
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