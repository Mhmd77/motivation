package com.tehran.motivation.library

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tehran.motivation.data.MediaType
import com.tehran.motivation.data.source.MediaRepository

class LibraryViewModel(repository: MediaRepository) : ViewModel() {

    val videosList = Transformations.map(repository.observeMedia(MediaType.Video)) {
        it?.map { media -> media.toVideo() }
    }
}

class LibraryViewModelFactory(private val repository: MediaRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LibraryViewModel(repository) as T
    }
}