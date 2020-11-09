package com.tehran.motivation.data.source.local.media

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tehran.motivation.data.Book
import com.tehran.motivation.data.Podcast
import com.tehran.motivation.data.Video

@Dao
interface MediaDao {
    @Query("delete from table_books")
    fun deleteAllBooks()

    @Query("delete from table_podcasts")
    fun deleteAllPodcast()

    @Query("delete from table_videos")
    fun deleteAllVideos()

    @Insert
    fun insertAllBooks(books: List<Book>)

    @Insert
    fun insertAllVideos(videos: List<Video>)

    @Insert
    fun insertAllPodcasts(podcast: List<Podcast>)

    @Query("select * from table_books")
    fun getBooks():List<Book>

    @Query("select * from table_videos")
    fun getVideos():List<Video>

    @Query("select * from table_podcasts")
    fun getPodcasts():List<Podcast>

    @Query("select * from table_books")
    fun observeBooks():LiveData<List<Book>>

    @Query("select * from table_videos")
    fun observeVideos():LiveData<List<Video>>

    @Query("select * from table_podcasts")
    fun observePodcasts():LiveData<List<Podcast>>
}