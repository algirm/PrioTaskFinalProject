package com.finalproject.priotask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finalproject.priotask.presentation.login.LoginScreen
import com.finalproject.priotask.presentation.login.LoginUiEvent
import com.finalproject.priotask.presentation.login.LoginUiIntent
import com.finalproject.priotask.presentation.login.LoginUiState
import com.finalproject.priotask.presentation.login.LoginViewModel
import com.finalproject.priotask.ui.theme.PrioTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrioTaskTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(it)
                    ) {
                        composable("login") {
                            val loginViewModel = hiltViewModel<LoginViewModel>()
                            val loginUiState: LoginUiState by loginViewModel.uiState.collectAsStateWithLifecycle()
                            LoginScreen(
                                uiState = loginUiState,
                                onEmailTextChange = { emailText ->
                                    loginViewModel.onIntent(LoginUiIntent.EmailTextChanged(emailText))
                                },
                                onPasswordTextChange = { passwordText ->
                                    loginViewModel.onIntent(
                                        LoginUiIntent.PasswordTextChanged(passwordText)
                                    )
                                },
                                onRegisterClick = { loginViewModel.onIntent(LoginUiIntent.RegisterClicked) },
                                onLoginClick = { loginViewModel.onIntent(LoginUiIntent.LoginClicked) }
                            )
                            LaunchedEffect(Unit) {
                                loginViewModel.event.collect { uiEvent ->
                                    when (val loginUiEvent = uiEvent as? LoginUiEvent) {
                                        LoginUiEvent.NavigateToRegisterScreen -> {
                                            navController.navigate(route = "register")
                                        }
                                        null -> {  }
                                    }
                                }
                            }
                        }
                        composable("register") {
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.Magenta)) {
                                Text(text = "This is register screen")
                            }
                        }
                    }
                }
            }
        }
    }
}