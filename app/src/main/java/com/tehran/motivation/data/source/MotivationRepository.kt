package com.tehran.motivation.data.source

import com.tehran.motivation.data.Motivation
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.source.local.motivation.LocalMotivationDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MotivationRepository(
    private val localDataSource: LocalMotivationDataSource,
    private val remoteDataSource: MotivationDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun refreshMotivations(): Result<Any> {
        return try {
            updateMotivationsFromRemote()
            Result.Success(Any())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getTodayMotivations(): Result<List<Motivation>> {
        return localDataSource.getTodayMotivations()
    }

    suspend fun getLastMotivation(): Result<Motivation> {
        return localDataSource.getMotivation()
    }

    private suspend fun updateMotivationsFromRemote() {
        val remoteMotivations = remoteDataSource.getMotivations(1)
        if (remoteMotivations is Result.Success) {
            localDataSource.deleteAllMotivations()
            localDataSource.insertMotivations(remoteMotivations.data)
        } else if (remoteMotivations is Result.Error) {
            throw remoteMotivations.exception
        }
    }


}
