package com.tehran.motivation.data.source

import androidx.lifecycle.LiveData
import com.tehran.motivation.data.Category
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.SubCategory

interface CategoryDataSource {

    fun observeCategories(): LiveData<Result<List<Category>>>

    suspend fun getCategories(): Result<List<Category>>

    suspend fun refreshCategories()

    fun observeCategory(categoryId: Long): LiveData<Result<Category>>

    suspend fun getCategory(categoryId: Long): Result<Category>

    suspend fun saveCategories(categories: List<Category>)

    suspend fun deleteAllCategories()

}