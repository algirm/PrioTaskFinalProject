package com.finalproject.priotask.presentation.register

data class RegisterUiState(
    val isLoading: Boolean = false,
    val emailText: String = "",
    val fullNameText: String = "",
    val passwordText: String = "",
    val errorMessage: String? = null
)
