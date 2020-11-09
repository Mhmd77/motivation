package com.tehran.motivation.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "motivation_table")
data class Motivation constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String? = "",
    val description: String = "",
    @ColumnInfo(name = "time_stamp")
    @Json(name = "timestamp")
    val time: String? = null
){

    //Todo : remove this function
    override fun toString(): String {
        return "des: $description \t time: $time"
    }
}