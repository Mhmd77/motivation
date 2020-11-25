package com.tehran.motivation.note

import androidx.lifecycle.*
import com.tehran.motivation.Event
import com.tehran.motivation.category.CategoryViewModel
import com.tehran.motivation.data.Motivation
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.source.MotivationRepository
import com.tehran.motivation.util.Constants
import com.tehran.motivation.util.PrefsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class NoteViewModel(
    private val repository: MotivationRepository
    , private val prefsManager: PrefsManager
) : ViewModel() {

    private val _snackMessage = MutableLiveData<Event<String>>()
    val snackMessage: LiveData<Event<String>> = _snackMessage

    val motivationList =
        repository.observeTodayMotivations(calculateNumber(), Constants.NUM_MOTIVATION)

    private fun calculateNumber(): Int {
        val lastUpdate = prefsManager.getUpdateMotivationDate() ?: return 0
        val today = Date()
        val diff = today.time - lastUpdate.time
        return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
            .toInt() * Constants.NUM_MOTIVATION)
    }

    fun changeFav(item:Motivation) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { repository.addToFav(item.id) }
            if (result is Result.Error)
                _snackMessage.postValue(Event(result.exception.message ?: ""))
        }
    }
}

@Suppress("UNCHECKED_CAST")
class NoteViewModelFactory(
    private val repository: MotivationRepository, private val prefsManager: PrefsManager
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteViewModel(repository, prefsManager) as T
    }
}