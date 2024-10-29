package com.codexdroid.pradnika.utils

import android.app.Application
import android.content.Context
import com.codexdroid.pradnika.room.CountryRepositoryMediator
import com.codexdroid.pradnika.room.RepositoryMediator
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltAndroidApp
class PradanApplication: Application() {

    lateinit var repositoryMediator: RepositoryMediator

    override fun onCreate() {
        super.onCreate()
        repositoryMediator = CountryRepositoryMediator(this)
    }
}