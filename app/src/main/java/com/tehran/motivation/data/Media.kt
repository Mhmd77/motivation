package com.tehran.motivation.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
open class Media constructor(
    var id: Long,
    var title: String,
    var description: String,
    var url: String
) {
    fun toVideo(): Video {
        return Video(id, title, description, url)
    }

    fun toBook(): Book {
        return Book(id, title, description, url)
    }

    fun toPodcast(): Podcast {
        return Podcast(id, title, description, url)
    }
}

@JsonClass(generateAdapter = true)
@Entity(tableName = "table_videos")
data class Video(
    @PrimaryKey
    var vId: Long = 0,
    var vTitle: String,
    var vDescription: String,
    var vUrl: String
) :
    Media(vId, vTitle, vDescription, vUrl)

@JsonClass(generateAdapter = true)
@Entity(tableName = "table_books")
data class Book(
    @PrimaryKey
    var bId: Long = 0,
    var bTitle: String,
    var bDescription: String,
    var vUrl: String
) :
    Media(bId, bTitle, bDescription, vUrl)

@JsonClass(generateAdapter = true)
@Entity(tableName = "table_podcasts")
data class Podcast(
    @PrimaryKey
    var pId: Long = 0,
    var pTitle: String,
    var pDescription: String,
    var vUrl: String
) :
    Media(pId, pTitle, pDescription, vUrl)

@JsonClass(generateAdapter = true)
data class MediaHolder(
    val videos: List<Media>,
    val books: List<Media>,
    val podcasts: List<Media>
)

enum class MediaType(val type: Int) {
    Video(1),
    Book(2),
    Podcast(3)
}
