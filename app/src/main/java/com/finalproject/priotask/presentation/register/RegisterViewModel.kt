package com.finalproject.priotask.presentation.register

import androidx.lifecycle.viewModelScope
import com.finalproject.priotask.common.BaseViewModel
import com.finalproject.priotask.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onIntent(registerUiIntent: RegisterUiIntent) {
        when (registerUiIntent) {
            is RegisterUiIntent.EmailTextChanged -> _uiState.update { it.copy(emailText = registerUiIntent.emailText) }
            is RegisterUiIntent.FullNameTextChanged -> _uiState.update { it.copy(fullNameText = registerUiIntent.fullNameText) }
            is RegisterUiIntent.PasswordTextChanged -> _uiState.update { it.copy(passwordText = registerUiIntent.passwordText) }
            RegisterUiIntent.RegisterClicked -> register()
            RegisterUiIntent.LoginHereClicked -> publishEvent(RegisterUiEvent.NavigateBackToLoginScreen)
        }
    }

    fun register() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        authRepository.registerWithEmailAndPassword(
            _uiState.value.fullNameText,
            _uiState.value.emailText,
            _uiState.value.passwordText
        ).collect { result ->
            result.onSuccess {
                publishEvent(RegisterUiEvent.RegisterSuccess)
                publishEvent(RegisterUiEvent.NavigateToHomeScreen)
            }.onFailure { t ->
                _uiState.update { it.copy(isLoading = false, errorMessage = t.message) }
            }
        }
    }

    fun errorMessageShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }

}