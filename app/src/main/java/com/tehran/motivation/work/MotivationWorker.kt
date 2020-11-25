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
import com.tehran.motivation.data.source.MotivationRepository
import com.tehran.motivation.util.sendNotification
import timber.log.Timber
import java.util.*

class MotivationWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val prefsManager = ServiceLocator.providePrefsManager(applicationContext)

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
        const val WORK_NAME = "com.tehran.motivation.work.MotivationWorker"
        const val WORK_TAG = "FetchMotivationJob"
    }

    override suspend fun doWork(): Result {
        val motivationRepository = ServiceLocator.provideMotivationRepository(applicationContext)
        return getMotivationsFromRemote(motivationRepository)
    }

    private suspend fun getMotivationsFromRemote(repository: MotivationRepository): Result {
        val id = prefsManager.getSelectedSubCategory()?.id
            ?: return Result.failure().also { Timber.e("There is no subcategory selected") }
        return when (val result = repository.refreshMotivations(id)) {
            is com.tehran.motivation.data.Result.Success -> {
      /*          getMotivation(repository)?.let {
                    notificationManager.sendNotification(it, applicationContext)
                }*/
                updateMotivationDate()
                Timber.d("Successful Motivation Update")
                Result.success()
            }
            is com.tehran.motivation.data.Result.Error -> {
                Timber.d("Retry: ${result.exception.message}")
                result.exception.printStackTrace()
                Result.retry()
            }
            else -> {
                Timber.d("Failed")
                Result.failure()
            }
        }
    }

    private fun updateMotivationDate() {
        prefsManager.updateMotivationDate(Date())
    }

    /*private suspend fun getMotivation(repository: MotivationRepository): String? {
        return when (val result = repository.getTodayMotivations()) {
            is com.tehran.motivation.data.Result.Success -> {
                val currentMotivation = prefsManager.getMotivationNumber().takeIf {
                    it < result.data.size && result.data.isNotEmpty()
                }.also {
                    if (it == null) {
                        prefsManager.resetMotivationNumber()
                    } else {
                        prefsManager.updateMotivationNumber()
                    }
                }
                currentMotivation?.let {
                    result.data[it].description
                } ?: kotlin.run {
                    null
                }
            }
            is com.tehran.motivation.data.Result.Error -> {
                Timber.e(result.exception)
                "No Motivation Is Left For Today. Sorry :("
            }
            else -> {
                "Loading"
            }
        }
    }*/


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