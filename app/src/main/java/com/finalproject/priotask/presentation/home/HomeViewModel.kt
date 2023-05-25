package com.finalproject.priotask.presentation.home

import androidx.lifecycle.viewModelScope
import com.finalproject.priotask.common.BaseViewModel
import com.finalproject.priotask.domain.model.Priority
import com.finalproject.priotask.domain.model.Task
import com.finalproject.priotask.domain.repository.AuthRepository
import com.finalproject.priotask.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val taskRepository: TaskRepository
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
    
    fun addUser() = viewModelScope.launch { 
        val task = Task(
            "1",
            "Tugas Kuliah",
            "Deskripsi Tugas Kuliah",
            Priority.Important,
            Date()
        )
        taskRepository.addTask(task).collect { result ->
            result.onSuccess { 
                
            }.onFailure { 
                
            }
        }
    }
    
}