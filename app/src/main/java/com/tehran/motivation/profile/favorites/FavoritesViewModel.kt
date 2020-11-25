package com.tehran.motivation.profile.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tehran.motivation.data.Motivation
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.source.MotivationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class FavoritesViewModel(private val repository: MotivationRepository) : ViewModel() {

    val motivationList = MutableLiveData<List<Motivation>>()

    init {
        refreshList()
    }

    private fun refreshList() {
        viewModelScope.launch() {
            val result = withContext(Dispatchers.Default) { repository.getFavorites() }
            if (result is Result.Success) {
                motivationList.postValue(result.data)
            } else {
                @Suppress("CAST_NEVER_SUCCEEDS")
                Timber.e((Result as Result.Error).exception)
            }
        }
    }

    fun removeFromFav(id: Long) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { repository.removeFromFav(id) }
            val flag = when (result) {
                is Result.Success -> {
                    result.data
                }
                else -> {
                    Timber.e((result as Result.Error).exception)
                    false
                }

            }
        }
        refreshList()
    }
}

@Suppress("UNCHECKED_CAST")
class FavoritesViewModelFactory(
    private val repository: MotivationRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoritesViewModel(repository) as T
    }
}