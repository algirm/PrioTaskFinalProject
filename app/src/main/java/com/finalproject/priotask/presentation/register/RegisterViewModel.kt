package com.finalproject.priotask.presentation.register

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.finalproject.priotask.common.BaseViewModel
import com.finalproject.priotask.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : BaseViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onIntent(registerUiIntent: RegisterUiIntent) {
        when (registerUiIntent) {
            is RegisterUiIntent.EmailTextChanged -> _uiState.update { it.copy(emailText = registerUiIntent.emailText) }
            is RegisterUiIntent.FullNameTextChanged -> _uiState.update { it.copy(fullNameText = registerUiIntent.fullNameText) }
            is RegisterUiIntent.PasswordTextChanged -> _uiState.update { it.copy(passwordText = registerUiIntent.passwordText) }
            RegisterUiIntent.RegisterClicked -> {
                viewModelScope.launch {
                    publishEvent(RegisterUiEvent.RegisterSuccess) // TODO
                }
            }
            RegisterUiIntent.LoginHereClicked -> {
                viewModelScope.launch {
                    publishEvent(RegisterUiEvent.NavigateBackToLoginScreen)
//                    delay(1000)
//                    publishEvent(object : UiEvent {})
                }
            }
        }
    }
    
    fun errorMessageShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }

}