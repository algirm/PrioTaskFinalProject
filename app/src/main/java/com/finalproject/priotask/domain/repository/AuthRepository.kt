package com.finalproject.priotask.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<Result<FirebaseUser>>
    
    fun isUserLoggedIn(): Flow<FirebaseUser?>
}