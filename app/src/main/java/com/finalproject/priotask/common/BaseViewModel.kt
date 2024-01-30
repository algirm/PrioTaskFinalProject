package com.finalproject.priotask.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel() : ViewModel() {
    
    protected val TAG: String = this.javaClass.simpleName

    private val eventChannel = Channel<UiEvent>(Channel.BUFFERED)
    val event = eventChannel.receiveAsFlow()
    
    init {
        Log.d(TAG, "$TAG initiated")
    }

    protected fun publishEvent(event: UiEvent) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "$TAG cleared")
    }

}