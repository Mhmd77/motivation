package com.tehran.motivation.category

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tehran.motivation.data.Category
import com.tehran.motivation.data.SubCategory

class CategoryViewModel : ViewModel() {
    val categoryList = MutableLiveData<List<Category>>()

    init {
        val categories = arrayListOf<Category>()
        val sub1 = arrayListOf<SubCategory>()
        sub1.add(SubCategory(title = "زیر 1"))
        sub1.add(SubCategory(title = "زیر 2"))
        sub1.add(SubCategory(title = "زیر 3"))
        sub1.add(SubCategory(title = "زیر 4"))
        categories.add(Category(title = "بالا 1", subCategories = sub1,color = "#FFC131F7"))
        categories.add(Category(title = "بالا 2", subCategories = sub1))
        categories.add(Category(title = "بالا 3", subCategories = sub1))
        categories.add(Category(title = "بالا 4", subCategories = sub1))
        categories.add(Category(title = "بالا 5", subCategories = sub1))
        categories.add(Category(title = "بالا 6", subCategories = sub1))
        categories.add(Category(title = "بالا 7", subCategories = sub1))
        categoryList.value = categories
    }

    fun onClick(item: SubCategory) {
        Log.i("MYTAG", "SubClicked: ${item.title}")
    }
}