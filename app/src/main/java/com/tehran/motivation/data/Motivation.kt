package com.tehran.motivation.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.*

@Entity(tableName = "motivation_table")
data class Motivation constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "entity_id")
    val id: Long = 0,
    @ColumnInfo(name = "title")
    val title: String? = "",
    @ColumnInfo(name = "description")
    @Json(name = "description")
    val note: String = ""/*,
    @ColumnInfo(name = "time_stamp")
    val time: Date*/
)