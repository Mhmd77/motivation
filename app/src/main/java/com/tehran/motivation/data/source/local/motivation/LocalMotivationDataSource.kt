package com.tehran.motivation.data.source.local.motivation

import androidx.lifecycle.LiveData
import com.tehran.motivation.data.Motivation
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.source.MotivationDataSource
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

    override suspend fun getTodayMotivations(): Result<List<Motivation>> =
        withContext(ioDispatcher) {
            try {
                val d = getDate()
                Timber.d(d)
                val motivations = dao.getTodayMotivations(d)
                motivations?.let {
                    return@withContext Result.Success(it)
                } ?: kotlin.run {
                    throw Exception("No Motivation")
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
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


    private fun getDate(): String {
        val c = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)

        //todo: uncomment this part        return Constants.dateFormat.format(c.time)

        return "2020-06-09T23:35:31"
    }
}