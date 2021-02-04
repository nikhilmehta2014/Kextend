package com.nikhil.library.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.MutableStateFlow

class ExampleViewModel : ViewModel() {

    private val _showProgress = MutableStateFlow(false)
    val showProgress = _showProgress.asLiveData()
}