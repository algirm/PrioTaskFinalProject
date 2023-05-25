package com.finalproject.priotask.data.repository

import android.util.Log
import com.finalproject.priotask.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

val defaultErrorAuthException = Exception("Error on authentication, please try again")

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    private val TAG = this@AuthRepositoryImpl.javaClass.simpleName

    override fun login(email: String, password: String): Flow<Result<FirebaseUser>> = callbackFlow {
        try {
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    authResult.user?.let { trySend(Result.success(it)) }
                        ?: trySend(Result.failure(defaultErrorAuthException))
                }.addOnFailureListener { e -> trySend(Result.failure(e)) }
        } catch (e: Exception) {
            e.printStackTrace()
            trySend(Result.failure(e))
        }
        awaitClose {
            Log.d(TAG, "closed login callback flow")
        }
    }

    override fun checkUserLogin(): FirebaseUser? = firebaseAuth.currentUser

    override fun registerWithEmailAndPassword(fullName: String, email: String, password: String) = callbackFlow<Result<FirebaseUser>> {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    authResult.user
                        ?.updateProfile(userProfileChangeRequest { displayName = fullName })
                        ?.addOnSuccessListener {
                            authResult.user?.let { user -> trySend(Result.success(user)) }
                                ?: trySend(Result.failure(defaultErrorAuthException))
                        }?.addOnFailureListener { trySend(Result.failure(it)) }
                }.addOnFailureListener {
                    trySend(Result.failure(it))
                }
        } catch (e: Exception) {
            e.printStackTrace()
            trySend(Result.failure(e))
        }
        awaitClose {
            Log.d(TAG, "closed register callback flow")
        }
    }
}