package com.tehran.motivation.data.source.local.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.tehran.motivation.data.Category
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.source.CategoryDataSource
import kotlinx.coroutines.*

class LocalCategoryDataSource(
    private val dao: CategoryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryDataSource {
    override fun observeCategories(): LiveData<Result<List<Category>>> {
        return Transformations.map(dao.observeCategories()) {
            try {
                it?.forEach { category ->
                    CoroutineScope(ioDispatcher).launch {
                        category.subcategories = dao.getSubCategories(category.id).subcategories
                    }
                }
                Result.Success(it)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun getCategories(): Result<List<Category>> = withContext(ioDispatcher) {
        try {
            val categories = dao.getCategories()
            categories.forEach { it.subcategories = dao.getSubCategories(it.id).subcategories}
            return@withContext Result.Success(categories)
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }

    }

    override suspend fun refreshCategories() {
        //No Op
    }

    override fun observeCategory(categoryId: Long): LiveData<Result<Category>> {
        TODO("subcategories must be added to category")
        return dao.observeCategory(categoryId).map {
            Result.Success(it)
        }
    }

    override suspend fun getCategory(categoryId: Long): Result<Category> =
        withContext(ioDispatcher) {
            return@withContext try {
                val category = dao.getCategory(categoryId)
                category.subcategories = dao.getSubCategories(categoryId).subcategories
                Result.Success(category)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }


    override suspend fun saveCategories(categories: List<Category>) = withContext(ioDispatcher) {
        dao.insertAllCategories(categories)
        categories.forEach { category ->
            category.subcategories?.let {
                dao.insertSubCategories(it)
            }
        }
    }

    override suspend fun deleteAllCategories() = withContext(ioDispatcher) {
        dao.deleteAllCategories()
        dao.deleteAllSubCategories()
    }
}