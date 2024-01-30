package com.finalproject.priotask.presentation.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val emailText: String = "",
    val passwordText: String = "",
    val errorMessage: String? = null
)
