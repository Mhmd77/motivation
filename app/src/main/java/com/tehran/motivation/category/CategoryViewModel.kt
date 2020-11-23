package com.tehran.motivation.category

import androidx.lifecycle.*
import com.tehran.motivation.ServiceLocator
import com.tehran.motivation.data.Category
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.SubCategory
import com.tehran.motivation.data.source.CategoryRepository
import com.tehran.motivation.data.succeeded
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class CategoryViewModel(repository: CategoryRepository) : ViewModel() {

    private var viewModelJob = Job()

    private val scope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val categoryList = repository.observeCategories().map {
        if (it is Result.Success) {
            it.data
        } else {
            null
        }
    }
//    val categoryList = MutableLiveData<List<Category>>()

    /*init {
        insertDummyCats()
    }

    private fun insertDummyCats() {
        val catList = arrayListOf<Category>()
        val subs = arrayListOf<SubCategory>().also {
            it.add(SubCategory(name = "خانواده", categoryId = 0))
            it.add(SubCategory(name = "خانواده", categoryId = 0))
            it.add(SubCategory(name = "خانواده", categoryId = 0))
            it.add(SubCategory(name = "خانواده", categoryId = 0))
        }
        val cat1 = Category(name = "روابط", color = "#ec5d57", subcategories = subs)
        catList.add(cat1)
        categoryList.value = catList
    }*/
}

@Suppress("UNCHECKED_CAST")
class CategoryViewModelFactory(private val repository: CategoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CategoryViewModel(repository) as T
}