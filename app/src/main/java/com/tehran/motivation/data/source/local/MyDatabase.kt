package com.tehran.motivation.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tehran.motivation.data.*
import com.tehran.motivation.data.source.local.category.CategoryDao
import com.tehran.motivation.data.source.local.media.MediaDao
import com.tehran.motivation.data.source.local.motivation.MotivationDao

@Database(
    entities = [Motivation::class, Category::class, SubCategory::class, Video::class, Book::class, Podcast::class],
    version = 11,
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun motivationDao(): MotivationDao
    abstract fun categoryDao(): CategoryDao
    abstract fun mediaDao(): MediaDao
}