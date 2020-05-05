package com.tehran.motivation

import android.app.Application
import android.content.Context
import timber.log.Timber
import timber.log.Timber.DebugTree


class MyApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}