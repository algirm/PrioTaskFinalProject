package com.finalproject.priotask.presentation.home

import com.finalproject.priotask.domain.model.Task
import com.finalproject.priotask.domain.model.User

data class HomeUiState(
    val isRefreshing: Boolean = false,
    val sortState: SortState = SortState.All,
    val user: User? = null,
    val tasks: List<Task> = emptyList(),
    val errorMessage: String? = null
)

sealed class SortState {
    object All : SortState()
    object Time : SortState()
    object Priority : SortState()
}
