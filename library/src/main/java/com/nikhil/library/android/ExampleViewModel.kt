package com.nikhil.library.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ExampleViewModel : ViewModel() {

    private val _showProgress = MutableStateFlow(false)
    val showProgress = _showProgress.asLiveData()

    init {
        // This Coroutine will be executed on Dispatchers.Main so it’s better to specify another dispatcher (such as Dispatcher.IO) if you have some heavy work
        viewModelScope.launch {

        }
    }
}