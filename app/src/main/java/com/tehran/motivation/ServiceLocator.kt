package com.tehran.motivation

import android.content.Context
import androidx.room.Room
import com.tehran.motivation.data.source.CategoryRepository
import com.tehran.motivation.data.source.MediaRepository
import com.tehran.motivation.data.source.MotivationRepository
import com.tehran.motivation.data.source.local.category.LocalCategoryDataSource
import com.tehran.motivation.data.source.local.MyDatabase
import com.tehran.motivation.data.source.local.media.LocalMediaDataSource
import com.tehran.motivation.data.source.local.motivation.LocalMotivationDataSource
import com.tehran.motivation.data.source.remote.RemoteCategoryDataSource
import com.tehran.motivation.data.source.remote.RemoteMediaDataSource
import com.tehran.motivation.data.source.remote.RemoteMotivationDataSource
import com.tehran.motivation.util.PrefsManager

object ServiceLocator {
    private var database: MyDatabase? = null

    @Volatile
    private var prefsManager: PrefsManager? = null

    @Volatile
    var categoryRepository: CategoryRepository? = null

    @Volatile
    var motivationRepository: MotivationRepository? = null

   @Volatile
    var mediaRepository: MediaRepository? = null

    fun providePrefsManager(context: Context): PrefsManager {
        synchronized(this) {
            return prefsManager ?: createPrefsManager(context)
        }
    }

    fun provideCategoryRepository(context: Context): CategoryRepository {
        synchronized(this) {
            return categoryRepository ?: createCategoryRepository(context)
        }
    }

    fun provideMotivationRepository(context: Context): MotivationRepository {
        synchronized(this) {
            return motivationRepository ?: createMotivationRepository(context)
        }
    }
    fun provideMediaRepository(context: Context): MediaRepository {
        synchronized(this) {
            return mediaRepository ?: createMediaRepository(context)
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


    private fun createMotivationRepository(context: Context): MotivationRepository {
        val newRepo =
            MotivationRepository(
                createMotivationLocalDataSource(context),
                RemoteMotivationDataSource
            )
        motivationRepository = newRepo
        return newRepo
    }
    private fun createMediaRepository(context: Context): MediaRepository {
        val newRepo =
            MediaRepository(
                RemoteMediaDataSource,
                createMediaLocalDataSource(context)
            )
        mediaRepository = newRepo
        return newRepo
    }
    private fun createCategoryLocalDataSource(context: Context): LocalCategoryDataSource {
        val database = database ?: createDatabase(context)
        return LocalCategoryDataSource(
            database.categoryDao()
        )
    }

    private fun createMotivationLocalDataSource(context: Context): LocalMotivationDataSource {
        val database = database ?: createDatabase(context)
        return LocalMotivationDataSource(
            database.motivationDao()
        )
    }
    private fun createMediaLocalDataSource(context: Context): LocalMediaDataSource {
        val database = database ?: createDatabase(context)
        return LocalMediaDataSource(
            database.mediaDao()
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

    private fun createPrefsManager(context: Context): PrefsManager {
        val newPrefsManager = PrefsManager(context)
        prefsManager = newPrefsManager
        return newPrefsManager
    }

}