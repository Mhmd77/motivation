package com.tehran.motivation.data.source.local.media

import androidx.lifecycle.LiveData
import com.tehran.motivation.data.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalMediaDataSource(
    private val dao: MediaDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun deleteAllMedias() = withContext(ioDispatcher) {
        dao.deleteAllBooks()
        dao.deleteAllVideos()
        dao.deleteAllPodcast()
    }

    suspend fun saveAllMedias(mediaHolder: MediaHolder) = withContext(ioDispatcher) {
        dao.insertAllBooks(mediaHolder.books.map { it.toBook() })
        dao.insertAllVideos(mediaHolder.videos.map { it.toVideo() })
        dao.insertAllPodcasts(mediaHolder.podcasts.map { it.toPodcast() })
    }

    suspend fun getMedias(): Result<MediaHolder> = withContext(ioDispatcher) {
        try {
            val books = dao.getBooks()
            val videos = dao.getVideos()
            val podcasts = dao.getPodcasts()
            return@withContext Result.Success(MediaHolder(videos, books, podcasts))
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    fun observeMediaByType(type: MediaType): LiveData<List<Media>?> {
        return when (type) {
            MediaType.Video -> dao.observeVideos()
            MediaType.Book -> dao.observeBooks()
            MediaType.Podcast -> dao.observePodcasts()
        } as LiveData<List<Media>?>
    }
}