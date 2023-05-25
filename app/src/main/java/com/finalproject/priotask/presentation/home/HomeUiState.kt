package com.finalproject.priotask.presentation.home

import com.finalproject.priotask.domain.model.User

data class HomeUiState(
    val isLoading: Boolean = false,
    val sortState: SortState = SortState.All,
    val user: User? = null
)

sealed class SortState {
    object All : SortState()
    object Time : SortState()
    object Priority : SortState()
}
