package com.finalproject.priotask

import androidx.lifecycle.viewModelScope
import com.finalproject.priotask.common.BaseViewModel
import com.finalproject.priotask.domain.repository.AuthRepository
import com.finalproject.priotask.presentation.login.LoginUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    
    init {
        viewModelScope.launch {
            authRepository.isUserLoggedIn().collect { user ->
                if (user == null) {
                    publishEvent(LoginUiEvent.NavigateToHomeScreen)
                }
            }
        }
    }
    
}