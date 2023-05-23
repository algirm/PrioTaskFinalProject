package com.finalproject.priotask.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.priotask.common.BaseViewModel
import com.finalproject.priotask.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()
    
    init {
        viewModelScope.launch {
            authRepository.isUserLoggedIn().collect { user ->
                if (user == null) {
                    _uiState.update { it.copy(isUserLoggedIn = true) }
                }
            }
        }
    }

    fun onIntent(loginUiIntent: LoginUiIntent) {
        when (loginUiIntent) {
            is LoginUiIntent.EmailTextChanged -> _uiState.update { it.copy(emailText = loginUiIntent.emailText) }
            is LoginUiIntent.PasswordTextChanged -> _uiState.update { it.copy(passwordText = loginUiIntent.passwordText) }
            LoginUiIntent.RegisterClicked -> {
                publishEvent(LoginUiEvent.NavigateToRegisterScreen)
            }

            LoginUiIntent.LoginClicked -> login()
        }
    }

    private fun login() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
//        delay(2000)
        authRepository.login(
            _uiState.value.emailText,
            _uiState.value.passwordText
        ).collect { result ->
            result.onSuccess {
                publishEvent(LoginUiEvent.NavigateToHomeScreen)
            }.onFailure { t ->
//                _uiState.update { it.copy(isLoading = false, errorMessage = t.message) }
//                publishEvent(LoginUiEvent.NavigateToHomeScreen)
            }
        }
    }
    
    fun errorMessageConsumed() {
        _uiState.update { it.copy(errorMessage = null) }
    }

}