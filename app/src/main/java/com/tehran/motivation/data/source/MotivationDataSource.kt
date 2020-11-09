package com.tehran.motivation.data.source

import androidx.lifecycle.LiveData
import com.tehran.motivation.data.Motivation
import com.tehran.motivation.data.Result

interface MotivationDataSource {

    suspend fun getMotivations(subId: Long): Result<List<Motivation>>

    fun observeMotivation(motivationId: Long): LiveData<Result<Motivation>>

    suspend fun getMotivation(): Result<Motivation>

    suspend fun getTodayMotivations(): Result<List<Motivation>>

    fun observeSavedMotivations(): LiveData<Result<List<Motivation>>>

    suspend fun getSavedMotivations(): Result<List<Motivation>>

    suspend fun saveMotivation(motivation: Motivation)

    suspend fun deleteAllSavedMotivations()

    suspend fun deleteSavedMotivation(motivationId: Long)

    suspend fun deleteAllMotivations()

    suspend fun insertMotivations(motivations: List<Motivation>)

}