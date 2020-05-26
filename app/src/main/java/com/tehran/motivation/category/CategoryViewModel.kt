package com.tehran.motivation.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import com.tehran.motivation.data.Category
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.source.CategoryRepository
import com.tehran.motivation.data.succeeded
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber

class CategoryViewModel(repository: CategoryRepository) : ViewModel() {

    private var viewModelJob = Job()

    private val scope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val categoryList = repository.observeCategories().map {
        if (it is Result.Success) {
            Timber.d("HORA")
            it.data
        } else {
            null
        }
    }

}

@Suppress("UNCHECKED_CAST")
class CategoryViewModelFactory(private val repository: CategoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CategoryViewModel(repository) as T
}