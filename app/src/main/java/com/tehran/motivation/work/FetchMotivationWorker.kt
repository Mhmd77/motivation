package com.tehran.motivation.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tehran.motivation.R
import com.tehran.motivation.ServiceLocator
import com.tehran.motivation.data.source.CategoryRepository
import com.tehran.motivation.util.sendNotification
import timber.log.Timber

class FetchMotivationWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val notificationManager by lazy {
        ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupNotificationChannel(
                applicationContext.getString(R.string.motivation_notification_channel_id),
                applicationContext.getString(R.string.motivation_notification_channel_name)
            )
        }
    }

    companion object {
        const val WORK_NAME = "com.tehran.motivation.work.FetchMotivationWorker"
        const val WORK_TAG = "FetchMotivationJob"
    }

    override suspend fun doWork(): Result {
        notificationManager.sendNotification("Network Awailable", applicationContext)
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


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupNotificationChannel(channelId: String, channelName: String) {
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description =
            applicationContext.getString(R.string.motivation_notification_channel_description)

        notificationManager.createNotificationChannel(notificationChannel)

    }
}