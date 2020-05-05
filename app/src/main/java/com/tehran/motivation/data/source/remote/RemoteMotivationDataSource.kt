package com.tehran.motivation.data.source.remote

import androidx.lifecycle.LiveData
import com.tehran.motivation.data.Motivation
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.source.MotivationDataSource

class RemoteMotivationDataSource:MotivationDataSource {
    override fun observeMotivations(): LiveData<Result<List<Motivation>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTasks(): Result<List<Motivation>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshMotivations() {
        TODO("Not yet implemented")
    }

    override fun observeMotivation(motivationId: Long): LiveData<Result<Motivation>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMotivation(motivationId: Long): Result<Motivation> {
        TODO("Not yet implemented")
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
}