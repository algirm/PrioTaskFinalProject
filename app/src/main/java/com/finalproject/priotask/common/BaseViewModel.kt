package com.finalproject.priotask.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel() : ViewModel() {

    private val eventChannel = Channel<UiEvent>(Channel.BUFFERED)
    val event = eventChannel.receiveAsFlow()

    protected fun publishEvent(event: UiEvent) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

}