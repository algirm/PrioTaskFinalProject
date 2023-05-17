package com.finalproject.priotask.presentation.login

import com.finalproject.priotask.common.UiEvent

sealed class LoginUiEvent : UiEvent {
    object NavigateToRegisterScreen : LoginUiEvent()
}
