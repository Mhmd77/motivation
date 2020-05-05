package com.tehran.motivation.data

data class Category constructor(
    val id: Long = 0,
    val title: String,
    val subCategories: List<SubCategory>,
    val color: String = "#00000000" // transparent white
)