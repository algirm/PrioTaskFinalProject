package com.finalproject.priotask.domain.repository

import com.finalproject.priotask.domain.model.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<Result<User>>
    
    fun getUser(): User?
    
    fun registerWithEmailAndPassword(fullName: String, email: String, password: String): Flow<Result<User>>
}