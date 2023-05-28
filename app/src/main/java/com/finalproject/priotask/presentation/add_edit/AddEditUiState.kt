package com.finalproject.priotask.presentation.add_edit

data class AddEditUiState(
    val isLoading: Boolean = false,
    val isEdit: Boolean = false,
    val errorMessage: String? = null
)