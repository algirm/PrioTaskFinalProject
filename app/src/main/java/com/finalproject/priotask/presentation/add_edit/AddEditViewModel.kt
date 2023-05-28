package com.finalproject.priotask.presentation.add_edit

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.finalproject.priotask.common.BaseViewModel
import com.finalproject.priotask.domain.model.Task
import com.finalproject.priotask.domain.usecase.AddTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AddEditUiState())
    val uiState = _uiState.asStateFlow()

    fun onIntent(addEditUiIntent: AddEditUiIntent) {
        when (addEditUiIntent) {
            is AddEditUiIntent.DoneEditTask -> {
//                _uiState.update { it.copy(isLoading = true) }
//                doneTask(addEditUiIntent.task)
            }

            is AddEditUiIntent.DoneTask -> {
//                _uiState.update { it.copy(isLoading = true) }
//                addTask(addEditUiIntent.task)
            }

            is AddEditUiIntent.AddTask -> {
//                _uiState.update { it.copy(isLoading = true) }
                addTask(addEditUiIntent.task)
            }
        }
    }

    private fun addTask(task: Task) = viewModelScope.launch {
        if (task.name.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Please fill the form") }
            return@launch
        }
        _uiState.update { it.copy(isLoading = true) }
        addTaskUseCase(task).collect { result ->
            Log.d(TAG, "doneTask: $result")
            result.onSuccess {
                publishEvent(AddEditUiEvent.Success)
            }.onFailure { e ->
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun errorMessageShown() = _uiState.update { it.copy(errorMessage = null) }
}