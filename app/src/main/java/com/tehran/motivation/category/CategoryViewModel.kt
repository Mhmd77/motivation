package com.tehran.motivation.category

import android.app.Application
import androidx.lifecycle.*
import com.tehran.motivation.MyApplication
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.SubCategory
import com.tehran.motivation.data.source.CategoryRepository
import com.tehran.motivation.util.PrefsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CategoryViewModel(
    private val repository: CategoryRepository,
    private val prefsManager: PrefsManager, application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    val selectedSubCategory = MutableLiveData<String>()

    init {
        updateSelectedSub()
    }

    private fun updateSelectedSub() {
        selectedSubCategory.value =
            String.format(
                "موضوع فعلی: %s",
                prefsManager.getSelectedSubCategory()?.name ?: "انتخاب نشده است"
            )
    }

    val categoryList = repository.observeCategories().map {
        if (it is Result.Success) {
            it.data
        } else {
            null
        }
    }

    fun selectSubCategory(item: SubCategory) {
        prefsManager.selectSubCategory(item)
        updateSelectedSub()
        getApplication<MyApplication>().setupFetchMotivationWork()
    }
}

@Suppress("UNCHECKED_CAST")
class CategoryViewModelFactory(
    private val repository: CategoryRepository,
    private val prefsManager: PrefsManager,
    private val application: Application
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CategoryViewModel(repository, prefsManager, application) as T
}