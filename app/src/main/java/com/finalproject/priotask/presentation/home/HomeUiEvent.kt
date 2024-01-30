package com.finalproject.priotask.presentation.home

import com.finalproject.priotask.common.UiEvent

sealed class HomeUiEvent : UiEvent {
    object NavigateToAddTaskScreen : HomeUiEvent()
}
