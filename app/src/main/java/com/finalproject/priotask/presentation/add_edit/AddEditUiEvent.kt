package com.finalproject.priotask.presentation.add_edit

import com.finalproject.priotask.common.UiEvent

sealed class AddEditUiEvent : UiEvent {
    object Success : AddEditUiEvent()
}
