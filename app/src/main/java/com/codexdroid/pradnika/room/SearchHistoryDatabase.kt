package com.codexdroid.pradnika.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TableSearchHistory::class],
    version = 1)
abstract class SearchHistoryDatabase : RoomDatabase() {

    abstract fun requestDao(): SearchHistoryDao

    companion object {
        private lateinit var database: SearchHistoryDatabase
        fun getDatabaseInstance(context: Context): SearchHistoryDatabase {
            synchronized(this) {
                if(!::database.isInitialized) {
                    database = Room.databaseBuilder(
                        context,
                        SearchHistoryDatabase::class.java,
                        "search_history").build()
                }
                return database
            }
        }
    }
}