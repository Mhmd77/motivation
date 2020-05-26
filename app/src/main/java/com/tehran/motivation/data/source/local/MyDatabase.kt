package com.tehran.motivation.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tehran.motivation.data.Category
import com.tehran.motivation.data.Motivation
import com.tehran.motivation.data.SubCategory
import com.tehran.motivation.data.source.local.category.CategoryDao

@Database(
    entities = [Motivation::class, Category::class, SubCategory::class],
    version = 5,
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun motivationDao(): MotivationDao
    abstract fun categoryDao(): CategoryDao
}