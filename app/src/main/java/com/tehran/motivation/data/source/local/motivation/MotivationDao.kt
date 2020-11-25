package com.tehran.motivation.data.source.local.motivation

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tehran.motivation.data.Motivation

@Dao
interface MotivationDao {
    @Insert
    fun insertMotivations(motivations: List<Motivation>)

    @Query("delete from motivation_table")
    fun deleteAllMotivations()

    @Query("select * from motivation_table limit :from, :n")
    suspend fun getTodayMotivations(from: Int, n: Int): List<Motivation>?

    @Query("select * from motivation_table limit :from , :n")
    fun observeTodayMotivations(from: Int, n: Int): LiveData<List<Motivation>?>

    @Query("select * from motivation_table limit 1")
    fun getMotivation(): Motivation?

}