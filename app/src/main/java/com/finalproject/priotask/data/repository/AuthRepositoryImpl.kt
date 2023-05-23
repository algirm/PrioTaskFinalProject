package com.finalproject.priotask.data.repository

import android.util.Log
import com.finalproject.priotask.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

val errorAuthException = Exception("Error on authentication, please try again")

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override fun login(email: String, password: String): Flow<Result<FirebaseUser>> = callbackFlow {
        try {
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    authResult.user?.let { trySend(Result.success(it)) } ?: trySend(
                        Result.failure(
                            errorAuthException
                        )
                    )
                }
                .addOnFailureListener { e ->
                    trySend(Result.failure(e.cause ?: errorAuthException))
                }
        } catch (e: Exception) {
            trySend(Result.failure(e.cause ?: errorAuthException))
        }
        awaitClose { 
            Log.d(this@AuthRepositoryImpl.javaClass.simpleName, "closed login callback flow")
        }
    }

    override fun isUserLoggedIn(): Flow<FirebaseUser?> = flow {
        emit(firebaseAuth.currentUser)
    }
}