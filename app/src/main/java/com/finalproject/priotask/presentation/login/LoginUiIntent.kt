package com.finalproject.priotask.presentation.login

sealed class LoginUiIntent {
    data class EmailTextChanged(val emailText: String) : LoginUiIntent()
    data class PasswordTextChanged(val passwordText: String) : LoginUiIntent()
    object RegisterClicked : LoginUiIntent()
    object LoginClicked : LoginUiIntent()
}
