package com.tehran.motivation.library

import androidx.lifecycle.*
import com.tehran.motivation.Event
import com.tehran.motivation.data.Book
import com.tehran.motivation.data.MediaType
import com.tehran.motivation.data.Video
import com.tehran.motivation.data.source.MediaRepository

class LibraryViewModel(repository: MediaRepository) : ViewModel() {

    private val _bookEvent = MutableLiveData<Event<String>>()
    val bookEvent: LiveData<Event<String>> = _bookEvent

    val videosList = Transformations.map(repository.observeMedia(MediaType.Video)) {
        it?.map { media -> media.toVideo() }
    }

    val booksList = Transformations.map(repository.observeMedia(MediaType.Book)) {
        it?.map { media -> media.toBook() }
    }

    fun bookOnClick(book:Book) {
        _bookEvent.value = Event(book.vUrl)
    }

}

@Suppress("UNCHECKED_CAST")
class LibraryViewModelFactory(private val repository: MediaRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LibraryViewModel(repository) as T
    }
}