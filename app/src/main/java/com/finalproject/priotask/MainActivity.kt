package com.finalproject.priotask

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.finalproject.priotask.domain.model.User
import com.finalproject.priotask.presentation.add_edit.AddEditScreen
import com.finalproject.priotask.presentation.add_edit.AddEditUiEvent
import com.finalproject.priotask.presentation.add_edit.AddEditUiIntent
import com.finalproject.priotask.presentation.add_edit.AddEditUiState
import com.finalproject.priotask.presentation.add_edit.AddEditViewModel
import com.finalproject.priotask.presentation.home.HomeScreen
import com.finalproject.priotask.presentation.home.HomeUiEvent
import com.finalproject.priotask.presentation.home.HomeUiIntent
import com.finalproject.priotask.presentation.home.HomeViewModel
import com.finalproject.priotask.presentation.home.SortState
import com.finalproject.priotask.presentation.login.*
import com.finalproject.priotask.presentation.register.*
import com.finalproject.priotask.ui.theme.PrioTaskTheme
import com.finalproject.priotask.util.collectWithLifecycle
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
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

    @OptIn(ExperimentalAnimationApi::class)
    private fun setContentApp() {
        setContent {
            PrioTaskTheme {
                val navController = rememberAnimatedNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { padding ->
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = startRoute,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable(
                            "login",
                            enterTransition = { slideInHorizontally(tween(500)) { -it } },
                            exitTransition = { slideOutHorizontally(tween(500)) { -it } }
                        ) {
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
                                        navController.navigate(route = "register") {
                                            popUpTo("login") { saveState = true }
//                                            launchSingleTop = true
                                            restoreState = true
                                        }
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
                                        snackbarHostState.showSnackbar(
                                            errorMessage,
                                            actionLabel = "OK"
                                        )
                                    }
                                }
                            }
                        }
                        composable(
                            "register",
                            enterTransition = { slideInHorizontally(tween(500)) { it } },
                            exitTransition = { slideOutHorizontally(tween(500)) { it } }
                        ) {
                            val registerViewModel = hiltViewModel<RegisterViewModel>()
                            val registerUiState: RegisterUiState by registerViewModel.uiState.collectAsStateWithLifecycle()
                            BackHandler {
                                navController.navigate("login") {
                                    popUpTo("register") {
                                        saveState = true
                                        inclusive = true
                                    }
                                    restoreState = true
                                }
                            }
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
//                                        navController.navigateUp()
                                        navController.navigate("login") {
                                            popUpTo("register") { 
                                                saveState = true 
                                                inclusive = true
                                            }
                                            restoreState = true
                                        }
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
                                        snackbarHostState.showSnackbar(
                                            errorMessage,
                                            actionLabel = "OK"
                                        )
                                    }
                                }
                            }
                        }
                        composable(
                            "home",
                            enterTransition = { slideInHorizontally(tween(500)) { -it } },
                            exitTransition = { slideOutHorizontally(tween(500)) { -it } }
                        ) {
                            val homeViewModel: HomeViewModel = hiltViewModel()
                            val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
                            val scope = rememberCoroutineScope()

                            HomeScreen(
                                uiState = homeUiState,
                                onAddTaskButtonClick = {
                                    homeViewModel.onIntent(HomeUiIntent.AddTaskClicked)
                                },
                                onSortingTypeClick = {
                                    val sortIntent = when (it) {
                                        SortState.All -> HomeUiIntent.SortingAllClicked
                                        SortState.Priority -> HomeUiIntent.SortingPriorityClicked
                                        SortState.Time -> HomeUiIntent.SortingTimeClicked
                                    }
                                    homeViewModel.onIntent(sortIntent)
                                },
                                onRefresh = { homeViewModel.onIntent(HomeUiIntent.RefreshContent) }
                            )

                            homeViewModel.event.collectWithLifecycle { uiEvent ->
                                when (uiEvent as? HomeUiEvent) {
                                    HomeUiEvent.NavigateToAddTaskScreen -> {
                                        navController.navigate("addtask")
                                    }

                                    null -> {}
                                }
                            }

                            LaunchedEffect(homeUiState.errorMessage) {
                                scope.launch {
                                    homeUiState.errorMessage?.let { errorMessage ->
                                        homeViewModel.errorMessageShown()
                                        snackbarHostState.showSnackbar(errorMessage, "OK")
                                    }
                                }
                            }
                        }
                        composable(
                            route = "addtask",
                            enterTransition = { slideInHorizontally(tween(500)) { it } },
                            exitTransition = { slideOutHorizontally(tween(500)) { it } }
                        ) {
                            val addEditViewModel: AddEditViewModel = hiltViewModel()
                            val addEditUiState by addEditViewModel.uiState.collectAsStateWithLifecycle()
                            val scope = rememberCoroutineScope()
                            AddEditScreen(
                                uiState = addEditUiState,
                                onAddEditClick = {
                                    addEditViewModel.onIntent(AddEditUiIntent.AddTask(it))
                                },
                                onArrowBackClick = {
                                    navController.navigateUp()
                                }
                            )
                            addEditViewModel.event.collectWithLifecycle() { event ->
                                when (event as? AddEditUiEvent) {
                                    AddEditUiEvent.Success -> {
                                        navController.navigateUp()
                                    }
                                    null -> {}
                                }
                            }
                            LaunchedEffect(addEditUiState.errorMessage) {
                                scope.launch {
                                    addEditUiState.errorMessage?.let { errorMessage ->
                                        addEditViewModel.errorMessageShown()
                                        snackbarHostState.showSnackbar(errorMessage, "OK")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}