package com.tehran.motivation.data.source

import androidx.lifecycle.LiveData
import com.tehran.motivation.data.Media
import com.tehran.motivation.data.MediaHolder
import com.tehran.motivation.data.MediaType
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.source.local.media.LocalMediaDataSource
import com.tehran.motivation.data.source.remote.RemoteMediaDataSource

class MediaRepository(
    private val remoteDataSource: RemoteMediaDataSource,
    private val localDataSource: LocalMediaDataSource
) {

    fun observeMedia(type: MediaType): LiveData<List<Media>?> {
        return localDataSource.observeMediaByType(type)
    }

    suspend fun getMedia(forceUpdate: Boolean): Result<MediaHolder> {
        if (forceUpdate) {
            try {
                updateMediaFromRemoteDataSource()
            } catch (ex: Exception) {
                return Result.Error(ex)
            }
        }
        return localDataSource.getMedias()
    }

    suspend fun refreshMedia(): Result<Any> {
        try {
            updateMediaFromRemoteDataSource()
        } catch (e: Exception) {
            return Result.Error(e)
        }
        return Result.Success(Any())
    }

    suspend fun updateMediaFromRemoteDataSource() {
        val remoteMedia = remoteDataSource.getMedias()
        if (remoteMedia is Result.Success) {
            localDataSource.deleteAllMedias()
            localDataSource.saveAllMedias(remoteMedia.data)
        } else if (remoteMedia is Result.Error) {
            throw remoteMedia.exception
        }
    }
}