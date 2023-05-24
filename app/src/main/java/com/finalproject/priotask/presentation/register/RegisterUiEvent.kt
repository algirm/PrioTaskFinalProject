package com.finalproject.priotask.presentation.register

import com.finalproject.priotask.common.UiEvent

sealed class RegisterUiEvent : UiEvent {
    object RegisterSuccess : RegisterUiEvent()
    object NavigateBackToLoginScreen : RegisterUiEvent()
    object NavigateToHomeScreen : RegisterUiEvent()
}
