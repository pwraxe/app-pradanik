package com.codexdroid.pradnika.room

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.codexdroid.pradnika.utils.PradanApplication
import com.codexdroid.pradnika.utils.loadJsonFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RoomViewModel(private val repository: SearchHistoryRepository): ViewModel() {

    private var _mobileNos = MutableStateFlow<List<TableSearchHistory>>(listOf())
    val mobileNos: StateFlow<List<TableSearchHistory>> = _mobileNos

    private var _countries = MutableStateFlow<List<Country>>(listOf())
    val countries: StateFlow<List<Country>> = _countries


    init {
        getSearchHistory()
    }
    fun insert(mobileData: TableSearchHistory) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.insert(mobileData)
        }
    }

    fun getSearchHistory() {
        viewModelScope.launch {
            repository.getMobileNos().collectLatest {
                _mobileNos.tryEmit(it)
            }
        }
    }

    fun remove(history: TableSearchHistory) {
        viewModelScope.launch (Dispatchers.IO){
            repository.remove(history)
        }
    }

    fun remove() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.remove()
        }
    }

    fun getCountry(context: Context) {
        viewModelScope.launch {
            _countries.tryEmit(loadJsonFile(context))
        }
    }
}


object ViewModelProvider {
    val factory = viewModelFactory {
        initializer {
            RoomViewModel(pradanApplication().repositoryMediator.countryRepository)
        }
    }
}

fun CreationExtras.pradanApplication():PradanApplication {
    return this[androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PradanApplication
}