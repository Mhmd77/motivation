package com.tehran.motivation.data

sealed class Media constructor(
    val id: Long,
    val title: String,
    val description: String
) {
    data class Video(val vId: Long, val vTitle: String, val vDescription: String) :
        Media(vId, vTitle, vDescription)

    data class Book(val bId: Long, val bTitle: String, val bDescription: String) :
        Media(bId, bTitle, bDescription)

    data class Podcast(val pId: Long, val pTitle: String, val pDescription: String) :
        Media(pId, pTitle, pDescription)
}

