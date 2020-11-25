package com.tehran.motivation.data.source

import androidx.lifecycle.LiveData
import com.tehran.motivation.data.Motivation
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.source.local.motivation.LocalMotivationDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import java.lang.Exception

class MotivationRepository(
    private val localDataSource: LocalMotivationDataSource,
    private val remoteDataSource: MotivationDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun refreshMotivations(subCatId: Long): Result<Any> {
        return try {
            updateMotivationsFromRemote(subCatId)
            Result.Success(Any())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getTodayMotivations(from: Int, n: Int): Result<List<Motivation>> {
        return localDataSource.getTodayMotivations(from, n)
    }

    fun observeTodayMotivations(from: Int, n: Int): LiveData<List<Motivation>?> {
        return localDataSource.observeTodayMotivations(from, n)
    }

    suspend fun getLastMotivation(): Result<Motivation> {
        return localDataSource.getMotivation()
    }

    private suspend fun updateMotivationsFromRemote(id: Long) {
        val remoteMotivations = remoteDataSource.getMotivations(id)
        if (remoteMotivations is Result.Success) {
            localDataSource.deleteAllMotivations()
            localDataSource.insertMotivations(remoteMotivations.data)
        } else if (remoteMotivations is Result.Error) {
            throw remoteMotivations.exception
        }
    }

    suspend fun getFavorites(): Result<List<Motivation>> {
        return remoteDataSource.getFavorites()
    }

    suspend fun addToFav(id: Long):Result<Boolean> {
        return remoteDataSource.addToFavorites(id)
    }

    suspend fun removeFromFav(id: Long):Result<Boolean> {
        return remoteDataSource.removeFromFav(id)
    }
}
