package com.finalproject.priotask.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<Result<FirebaseUser>>
    
    suspend fun checkUserLogin(): FirebaseUser?
    
    fun registerWithEmailAndPassword(fullName: String, email: String, password: String): Flow<Result<FirebaseUser>>
}