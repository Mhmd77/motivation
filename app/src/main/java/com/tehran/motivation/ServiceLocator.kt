package com.tehran.motivation

import android.content.Context
import androidx.room.Room
import com.tehran.motivation.data.source.CategoryRepository
import com.tehran.motivation.data.source.local.category.LocalCategoryDataSource
import com.tehran.motivation.data.source.local.MyDatabase
import com.tehran.motivation.data.source.remote.RemoteCategoryDataSource

object ServiceLocator {
    private var database: MyDatabase? = null

    @Volatile
    var categoryRepository: CategoryRepository? = null

    fun provideCategoryRepository(context: Context): CategoryRepository {
        synchronized(this) {
            return categoryRepository ?: createCategoryRepository(context)
        }
    }

    private fun createCategoryRepository(context: Context): CategoryRepository {
        val newRepo =
            CategoryRepository(
                RemoteCategoryDataSource,
                createCategoryLocalDataSource(context)
            )
        categoryRepository = newRepo
        return newRepo
    }

    private fun createCategoryLocalDataSource(context: Context): LocalCategoryDataSource {
        val database = database ?: createDatabase(context)
        return LocalCategoryDataSource(
            database.categoryDao()
        )
    }

    private fun createDatabase(context: Context): MyDatabase {
        return Room.databaseBuilder(
            context,
            MyDatabase::class.java,
            "MotivationDatabase"
        ).fallbackToDestructiveMigration()
            .build().also {
                database = it
            }
    }

}