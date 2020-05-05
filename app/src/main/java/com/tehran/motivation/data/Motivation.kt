package com.tehran.motivation.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "motivation_table")
data class Motivation constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "entity_id")
    val id: Long = 0,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "note")
    val note: String = ""
)