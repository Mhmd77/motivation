package com.tehran.motivation

import android.app.Application
import android.content.Context
import androidx.work.*
import com.tehran.motivation.work.FetchMotivationWorker
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

        fun cancleService(tag: String) {
            WorkManager.getInstance().cancelAllWorkByTag(tag)
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
            PeriodicWorkRequestBuilder<FetchMotivationWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .addTag(FetchMotivationWorker.WORK_TAG)
                .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            FetchMotivationWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            repeatedFetch
        )
    }
}