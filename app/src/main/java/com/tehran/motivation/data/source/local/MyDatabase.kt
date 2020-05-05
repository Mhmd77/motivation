package com.tehran.motivation.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tehran.motivation.data.Motivation

@Database(entities = [Motivation::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun motivationDao(): MotivationDao
}