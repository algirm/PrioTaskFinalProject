package com.finalproject.priotask

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finalproject.priotask.presentation.login.LoginScreen
import com.finalproject.priotask.presentation.login.LoginUiEvent
import com.finalproject.priotask.presentation.login.LoginUiIntent
import com.finalproject.priotask.presentation.login.LoginUiState
import com.finalproject.priotask.presentation.login.LoginViewModel
import com.finalproject.priotask.presentation.register.RegisterScreen
import com.finalproject.priotask.presentation.register.RegisterUiEvent
import com.finalproject.priotask.presentation.register.RegisterUiIntent
import com.finalproject.priotask.presentation.register.RegisterUiState
import com.finalproject.priotask.presentation.register.RegisterViewModel
import com.finalproject.priotask.ui.theme.PrioTaskTheme
import com.finalproject.priotask.util.collectWithLifecycle
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrioTaskTheme {
                val navController: NavHostController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) }
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
                                        null -> {}
                                    }
                                }
                            }
                        }
                        composable("register") {
                            val registerViewModel = hiltViewModel<RegisterViewModel>()
                            val registerUiState: RegisterUiState by registerViewModel.uiState.collectAsStateWithLifecycle()
                            RegisterScreen(
                                uiState = registerUiState,
                                onEmailTextChange = { emailText ->
                                    registerViewModel.onIntent(
                                        RegisterUiIntent.EmailTextChanged(
                                            emailText
                                        )
                                    )
                                },
                                onFullNameTextChange = { fullNameText ->
                                    registerViewModel.onIntent(
                                        RegisterUiIntent.FullNameTextChanged(
                                            fullNameText
                                        )
                                    )
                                },
                                onPasswordTextChange = { passwordText ->
                                    registerViewModel.onIntent(
                                        RegisterUiIntent.PasswordTextChanged(passwordText)
                                    )
                                },
                                onRegisterClick = { registerViewModel.onIntent(RegisterUiIntent.RegisterClicked) },
                                onLoginHereClick = { registerViewModel.onIntent(RegisterUiIntent.LoginHereClicked) }
                            )
                            val scope = rememberCoroutineScope()
                            LaunchedEffect(Unit) {
                                registerViewModel.event.collect { uiEvent ->
                                    when (val registerUiEvent = uiEvent as? RegisterUiEvent) {
                                        RegisterUiEvent.RegisterSuccess -> {
                                            scope.launch {
                                                snackbarHostState.showSnackbar("Register Success")
                                                Log.d("TAG", "onCreate: snackbar shown")
                                            }
                                        }
                                        RegisterUiEvent.NavigateBackToLoginScreen -> {
                                            Log.d("TAG", "register event: ${Random.nextInt()}")
                                            navController.navigateUp()
                                            Log.d("TAG", "register event: after NavigateUp")
                                        }
                                        null -> { /* todo error wrong event type sent here */ }
                                    }
                                }
                            }
//                            registerViewModel.event.collectWithLifecycle(minActiveState = Lifecycle.State.RESUMED) { uiEvent ->
//                                when (val registerUiEvent = uiEvent as? RegisterUiEvent) {
//                                    RegisterUiEvent.RegisterSuccess -> {
//                                        snackbarHostState.showSnackbar("Register Success")
//                                    }
//                                    RegisterUiEvent.NavigateBackToLoginScreen -> {
//                                        Log.d("TAG", "register event: ${Random.nextInt()}")
//                                        navController.navigateUp()
//                                    }
//                                    null -> {
////                                        Log.d("TAG", "register event: ${Random.nextInt()}")
//                                    }
//                                }
//                            }
                        }
                    }
                }
            }
        }
    }
}