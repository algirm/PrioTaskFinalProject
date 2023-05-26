package com.finalproject.priotask.presentation.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.finalproject.priotask.common.BaseViewModel
import com.finalproject.priotask.domain.model.Priority
import com.finalproject.priotask.domain.model.Task
import com.finalproject.priotask.domain.repository.AuthRepository
import com.finalproject.priotask.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val getTasksUseCase: GetTasksUseCase
) : BaseViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()
    
    init {
        viewModelScope.launch { 
            getUser()
        }
    }
    
    fun onIntent(homeUiIntent: HomeUiIntent) {
        when (homeUiIntent) {
            HomeUiIntent.SortingAllClicked -> _uiState.update { it.copy(sortState = SortState.All) }
            HomeUiIntent.SortingPriorityClicked -> _uiState.update { it.copy(sortState = SortState.Priority) }
            HomeUiIntent.SortingTimeClicked -> _uiState.update { it.copy(sortState = SortState.Time) }
            HomeUiIntent.RefreshContent -> {
                getUser(true)
            }
        }
    }
    
    private fun getUser(forceRefresh: Boolean = false) {
        _uiState.update { it.copy(isRefreshing = true) }
        val user = authRepository.getUser()
        _uiState.update { 
            it.copy(user = user)
        }
        viewModelScope.launch { 
            getTasksUseCase(forceRefresh).collect { result ->
                result.onSuccess {  resultTasks ->
                    Log.d(TAG, "getUser: $resultTasks")
                    _uiState.update { it.copy(isRefreshing = false, tasks = resultTasks) }
                }.onFailure { e ->
                    Log.e(TAG, "getUser: failed get tasks", e)
                    _uiState.update { it.copy(isRefreshing = false, errorMessage = e.message) }
                }
            }
        } //TODO UNCOMMENT AFTER IMPL COLLAPSING TOOLBAR & TASK CARD READY
    }
    
    fun addTask() = viewModelScope.launch { 
        val task = Task(
            "1",
            "Tugas Kuliah",
            "Deskripsi Tugas Kuliah",
            Priority.Moderate,
            Date()
        )
//        taskRepository.addTask(task).collect { result ->
//            result.onSuccess {
//                Log.d(TAG, "addTask: success add task")
//            }.onFailure {
//                Log.d(TAG, "addTask: failed add task")
//            }
//        }
    }
    
    fun errorMessageShown() = _uiState.update { it.copy(errorMessage = null) }
    
}