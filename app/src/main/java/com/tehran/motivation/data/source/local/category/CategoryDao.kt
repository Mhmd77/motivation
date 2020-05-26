package com.tehran.motivation.data.source.local.category

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tehran.motivation.data.Category
import com.tehran.motivation.data.CategoryWithAllSubCategories
import com.tehran.motivation.data.SubCategory

@Dao
interface CategoryDao {

    @Query("select * from table_category")
    fun observeCategories(): LiveData<List<Category>>

    @Query("select * from table_category")
    suspend fun getCategories(): List<Category>

    @Query("select * from table_category where id = :id")
    fun observeCategory(id: Long): LiveData<Category>

    @Query("select * from table_category where id = :id")
    suspend fun getCategory(id: Long): Category

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(list: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubCategories(subCategories: List<SubCategory>)

    @Query("delete from table_category")
    suspend fun deleteAllCategories()

    @Query("delete from table_subcategory")
    suspend fun deleteAllSubCategories()

    @Transaction
    @Query("select * from table_category where id = :id")
    suspend fun getSubCategories(id: Long): CategoryWithAllSubCategories
}