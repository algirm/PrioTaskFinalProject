package com.finalproject.priotask

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finalproject.priotask.domain.model.User
import com.finalproject.priotask.presentation.home.HomeScreen
import com.finalproject.priotask.presentation.home.HomeViewModel
import com.finalproject.priotask.presentation.login.*
import com.finalproject.priotask.presentation.register.*
import com.finalproject.priotask.ui.theme.PrioTaskTheme
import com.finalproject.priotask.util.collectWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private var startRoute = "login"
    private var userLoggedIn: User? = null
    private var isLoading: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val appSplashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        appSplashScreen.setKeepOnScreenCondition { isLoading }
        userLoggedIn = mainViewModel.checkUserLogin()
        if (userLoggedIn != null) startRoute = "home"
        setContentApp()
        isLoading = false
    }
    
    private fun setContentApp() {
        setContent {
            PrioTaskTheme {
                val navController: NavHostController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = startRoute,
                        modifier = Modifier.padding(padding)
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
                                onLoginClick = { loginViewModel.onIntent(LoginUiIntent.LoginClicked) },
                                modifier = Modifier.padding(padding)
                            )
                            loginViewModel.event.collectWithLifecycle(minActiveState = Lifecycle.State.RESUMED) { uiEvent ->
                                when (uiEvent as? LoginUiEvent) {
                                    LoginUiEvent.NavigateToRegisterScreen -> {
                                        navController.navigate(route = "register")
                                    }
                                    LoginUiEvent.NavigateToHomeScreen -> {
                                        navController.navigate("home") {
                                            popUpTo("login") {
                                                inclusive = true
                                            }
                                        }
                                    }
                                    null -> {}
                                }
                            }
                            val scope = rememberCoroutineScope()
                            LaunchedEffect(loginUiState.errorMessage) {
                                scope.launch {
                                    loginUiState.errorMessage?.let { errorMessage ->
                                        loginViewModel.errorMessageConsumed()
                                        snackbarHostState.showSnackbar(errorMessage, actionLabel = "OK")
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
                            registerViewModel.event.collectWithLifecycle(minActiveState = Lifecycle.State.RESUMED) { uiEvent ->
                                when (uiEvent as? RegisterUiEvent) {
                                    RegisterUiEvent.RegisterSuccess -> {
                                        scope.launch {
                                            Log.d("TAG", "onCreate: snackbar before show")
                                            snackbarHostState.showSnackbar("Register Success")
                                            Log.d("TAG", "onCreate: snackbar shown")
                                        }
                                    }

                                    RegisterUiEvent.NavigateBackToLoginScreen -> {
                                        Log.d("TAG", "register event: ${Random.nextInt()}")
                                        navController.navigateUp()
                                        Log.d("TAG", "register event: after NavigateUp")
                                    }
                                    RegisterUiEvent.NavigateToHomeScreen -> {
                                        navController.navigate("home") {
                                            popUpTo("login") {
                                                inclusive = true
                                            }
                                        }
                                    }
                                    null -> { /* todo error wrong event type sent here */
                                    }
                                }
                            }
//                            }
                            LaunchedEffect(registerUiState.errorMessage) {
                                scope.launch {
                                    registerUiState.errorMessage?.let { errorMessage ->
                                        registerViewModel.errorMessageShown()
                                        snackbarHostState.showSnackbar(errorMessage, actionLabel = "OK")
                                    }
                                }
                            }
                        }
                        composable("home") {
                            val homeViewModel: HomeViewModel = hiltViewModel()
                            val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
                            HomeScreen(
                                uiState = homeUiState
                            )
                        }
                    }
                }
            }
        }
    }
}