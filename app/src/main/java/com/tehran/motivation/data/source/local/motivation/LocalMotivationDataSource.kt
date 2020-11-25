package com.tehran.motivation.data.source.local.motivation

import androidx.lifecycle.LiveData
import com.tehran.motivation.data.Motivation
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.source.MotivationDataSource
import com.tehran.motivation.util.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*

class LocalMotivationDataSource internal constructor(
    private val dao: MotivationDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MotivationDataSource {

    override suspend fun getMotivations(subId: Long): Result<List<Motivation>> {
        TODO("Not yet implemented")
    }

    override fun observeMotivation(motivationId: Long): LiveData<Result<Motivation>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMotivation(): Result<Motivation> = withContext(ioDispatcher) {
        try {
            val motivation = dao.getMotivation()
            motivation?.let {
                return@withContext Result.Success(it)
            } ?: kotlin.run {
                throw Exception("No Motivation")
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun getTodayMotivations(from: Int, num: Int): Result<List<Motivation>> =
        withContext(ioDispatcher) {
            try {
                val motivations = dao.getTodayMotivations(from, num)
                motivations?.let {
                    return@withContext Result.Success(it)
                } ?: kotlin.run {
                    throw Exception("No Motivation")
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    override fun observeTodayMotivations(from: Int, num: Int): LiveData<List<Motivation>?> {
        return dao.observeTodayMotivations(from, num)
    }

    override fun observeSavedMotivations(): LiveData<Result<List<Motivation>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSavedMotivations(): Result<List<Motivation>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveMotivation(motivation: Motivation) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllSavedMotivations() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSavedMotivation(motivationId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllMotivations() {
        dao.deleteAllMotivations()
    }

    override suspend fun insertMotivations(motivations: List<Motivation>) {
        dao.insertMotivations(motivations)
    }

    override suspend fun getFavorites(): Result<List<Motivation>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToFavorites(id: Long):Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromFav(id: Long): Result<Boolean> {
        TODO("Not yet implemented")
    }
}