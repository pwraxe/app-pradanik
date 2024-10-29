package com.codexdroid.pradnika.room

import kotlinx.coroutines.flow.Flow


interface SearchHistoryRepository {

    fun getMobileNos(): Flow<List<TableSearchHistory>>

    suspend fun insert(tableSearchHistory: TableSearchHistory)
    suspend fun remove(tableSearchHistory: TableSearchHistory)
    suspend fun remove()
}

class CountryRepositoryImpl(private val dao: SearchHistoryDao): SearchHistoryRepository {

    override fun getMobileNos(): Flow<List<TableSearchHistory>> {
        return dao.getSearchedMobileNos()
    }

    override suspend fun insert(tableSearchHistory: TableSearchHistory) {
        dao.insert(tableSearchHistory)
    }

    override suspend fun remove(tableSearchHistory: TableSearchHistory) {
        dao.remove(historyData = tableSearchHistory)
    }

    override suspend fun remove() {
        dao.remove()
    }


}