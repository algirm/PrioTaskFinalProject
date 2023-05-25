package com.finalproject.priotask.presentation.home

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
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()
    
    init {
        viewModelScope.launch { 
            getUser()
        }
    }
    
    private fun getUser() {
        val user = authRepository.getUser()
        _uiState.update { 
            it.copy(user = user)
        }
    }
    
}