package com.tehran.motivation.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Entity(tableName = "table_category")
@JsonClass(generateAdapter = true)
data class Category constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var name: String = "",
    @Ignore
    var subcategories: List<SubCategory>? = null,
    @Ignore
    var subcategoriesLiveData: LiveData<List<SubCategory>>? = null,
    var color: String? = "#ec5d57" // transparent white
)

@Entity(tableName = "table_subcategory")
@JsonClass(generateAdapter = true)
data class SubCategory constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    @ColumnInfo(name = "category_id")
    @Json(name="category_id")
    var categoryId: Long = 0,
    @ColumnInfo(name = "icon_url")
    @Json(name="icon_url")
    var iconUrl: String = ""
)

data class CategoryWithAllSubCategories(
    @Embedded
    var category: Category? = null,
    @Relation(
        entity = SubCategory::class,
        parentColumn = "id",
        entityColumn = "category_id"
    )
    var subcategories: List<SubCategory>? = null
)
