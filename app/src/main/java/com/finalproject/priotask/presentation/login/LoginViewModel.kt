package com.finalproject.priotask.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.priotask.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onIntent(loginUiIntent: LoginUiIntent) {
        when (loginUiIntent) {
            is LoginUiIntent.EmailTextChanged -> _uiState.update { it.copy(emailText = loginUiIntent.emailText) }
            is LoginUiIntent.PasswordTextChanged -> _uiState.update { it.copy(passwordText = loginUiIntent.passwordText) }
            LoginUiIntent.RegisterClicked -> {
                publishEvent(LoginUiEvent.NavigateToRegisterScreen)
            }
            LoginUiIntent.LoginClicked -> {
                
            }
        }
    }

}