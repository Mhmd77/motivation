package com.tehran.motivation.data.source

import androidx.lifecycle.LiveData
import com.tehran.motivation.data.Motivation
import com.tehran.motivation.data.Result

interface MotivationDataSource {


    fun observeMotivations(): LiveData<Result<List<Motivation>>>

    suspend fun getTasks(): Result<List<Motivation>>

    suspend fun refreshMotivations()

    fun observeMotivation(motivationId: Long): LiveData<Result<Motivation>>

    suspend fun getMotivation(motivationId: Long): Result<Motivation>

    fun observeSavedMotivations():LiveData<Result<List<Motivation>>>

    suspend fun getSavedMotivations():Result<List<Motivation>>

    suspend fun saveMotivation(motivation: Motivation)

    suspend fun deleteAllSavedMotivations()

    suspend fun deleteSavedMotivation(motivationId: Long)
}