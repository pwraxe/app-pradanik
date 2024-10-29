package com.codexdroid.pradnika.room

import android.content.Context

interface RepositoryMediator {
    val countryRepository: SearchHistoryRepository
}

class CountryRepositoryMediator(private val context: Context):RepositoryMediator {
    override val countryRepository: SearchHistoryRepository by lazy {
        CountryRepositoryImpl(SearchHistoryDatabase.getDatabaseInstance(context).requestDao())
    }

}