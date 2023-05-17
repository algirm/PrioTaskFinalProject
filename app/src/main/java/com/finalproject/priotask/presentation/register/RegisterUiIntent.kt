package com.finalproject.priotask.presentation.register

sealed class RegisterUiIntent {
    data class EmailTextChanged(val emailText: String) : RegisterUiIntent()
    data class FullNameTextChanged(val fullNameText: String) : RegisterUiIntent()
    data class PasswordTextChanged(val passwordText: String) : RegisterUiIntent()
    object RegisterClicked : RegisterUiIntent()
    object LoginHereClicked : RegisterUiIntent()
}
