package com.tehran.motivation

import android.app.Application
import android.content.Context
import androidx.work.*
import com.tehran.motivation.work.CategoryWorker
import com.tehran.motivation.work.MediaWorker
import com.tehran.motivation.work.MotivationWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.concurrent.TimeUnit


class MyApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    init {
        instance = this
    }

    private fun delayedInit() {
        applicationScope.launch {
            Timber.plant(DebugTree())
        }
    }

    companion object {
        private var instance: MyApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun cancleService(context: Context, tag: String) {
            WorkManager.getInstance(context).cancelAllWorkByTag(tag)
        }
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .build()
        val repeatedFetch =
            PeriodicWorkRequestBuilder<CategoryWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .addTag(CategoryWorker.WORK_TAG)
                .build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            CategoryWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            repeatedFetch
        )
    }

    fun setupRecurringMediaWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .build()
        val repeatedFetch =
            PeriodicWorkRequestBuilder<MediaWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .addTag(MediaWorker.WORK_TAG)
                .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            MediaWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            repeatedFetch
        )
    }

    fun setupFetchMotivationWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .build()
        val repeatedFetch =
            PeriodicWorkRequestBuilder<MotivationWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .addTag(MotivationWorker.WORK_TAG)
                .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            MotivationWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            repeatedFetch
        )
    }
}