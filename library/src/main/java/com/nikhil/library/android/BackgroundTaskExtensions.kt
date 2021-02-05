package com.nikhil.library.android

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikhil.library.util.ControlledRunner
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun ViewModel.loadRequest(context: Context, block: suspend () -> Unit): Job {
    return viewModelScope.launch {
        try {
            block()
        } catch (ex: Exception) {
            Toast.makeText(context, ex.message, Toast.LENGTH_SHORT).show()
        }
    }
}

suspend fun <T : Any> ControlledRunner<T>.cancelLastRequestThenRun(block: suspend () -> T): T {
    return cancelPreviousThenRun(block)
}

suspend fun <T : Any> ControlledRunner<T>.joinLastRequestThenRun(block: suspend () -> T): T {
    return joinPreviousOrRun(block)
}