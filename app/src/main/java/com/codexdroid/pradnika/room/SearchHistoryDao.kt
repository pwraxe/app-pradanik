package com.codexdroid.pradnika.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchedData: TableSearchHistory)

    @Transaction
    @Query("SELECT * FROM table_entered_mob_no ORDER BY timeInMilliSec DESC")
    fun getSearchedMobileNos(): Flow<List<TableSearchHistory>>

    @Transaction
    @Delete
    fun remove(historyData: TableSearchHistory)

    @Transaction
    @Query("DELETE FROM table_entered_mob_no")
    fun remove()

}