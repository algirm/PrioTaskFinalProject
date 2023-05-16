package com.finalproject.priotask.presentation.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val userNameText: String = "",
    val passwordText: String = "",
    val errorMessage: String? = null
)
