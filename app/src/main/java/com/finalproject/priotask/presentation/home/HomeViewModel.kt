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
            HomeUiIntent.SortingAllClicked -> {
                _uiState.update {
                    it.copy(
                        sortState = SortState.All,
                        tasks = sortList(it.tasks, SortState.All)
                    )
                }
            }

            HomeUiIntent.SortingPriorityClicked -> {
                _uiState.update {
                    it.copy(
                        sortState = SortState.Priority,
                        tasks = sortList(it.tasks, SortState.Priority)
                    )
                }
            }

            HomeUiIntent.SortingTimeClicked -> {
                _uiState.update {
                    it.copy(
                        sortState = SortState.Time,
                        tasks = sortList(it.tasks, SortState.Time)
                    )
                }
            }

            HomeUiIntent.RefreshContent -> {
                getUser(true)
            }

            HomeUiIntent.AddTaskClicked -> publishEvent(HomeUiEvent.NavigateToAddTaskScreen)
        }
    }

    private fun sortList(tasks: List<Task>, sortState: SortState): List<Task> {
        return when (sortState) {
            SortState.All -> {
                tasks.sortedBy { it.deadline }
            }

            SortState.Time -> {
                tasks.sortedBy { it.deadline }
            }

            SortState.Priority -> {
                tasks.sortedBy {
                    when (it.priority) {
                        Priority.High -> 2
                        Priority.Low -> 0
                        Priority.Moderate -> 1
                    }
                }
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
                result.onSuccess { resultTasks ->
                    Log.d(TAG, "getUser: $resultTasks")
                    _uiState.update {
                        it.copy(
                            isRefreshing = false,
                            tasks = sortList(resultTasks, _uiState.value.sortState)
                        )
                    }
                }.onFailure { e ->
                    Log.e(TAG, "getUser: failed get tasks", e)
                    _uiState.update { it.copy(isRefreshing = false, errorMessage = e.message) }
                }
            }
        }
    }

    fun errorMessageShown() = _uiState.update { it.copy(errorMessage = null) }

}