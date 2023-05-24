package com.finalproject.priotask.data.repository

import android.util.Log
import com.finalproject.priotask.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
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

    override suspend fun checkUserLogin(): FirebaseUser? = withContext(Dispatchers.IO) {
//        delay(1000)
        val user = firebaseAuth.currentUser
        return@withContext firebaseAuth.currentUser
    }

    override fun registerWithEmailAndPassword(fullName: String, email: String, password: String) = callbackFlow<Result<FirebaseUser>> {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    Log.d(TAG, "registerWithEmailAndPassword: im here bro")
                    authResult.user
                        ?.updateProfile(userProfileChangeRequest { displayName = fullName })
                        ?.addOnSuccessListener {
                            Log.d(TAG, "registerWithEmailAndPassword: success register and add display name")
                            Log.d(TAG, "registerWithEmailAndPassword: current user is ${firebaseAuth.currentUser}")
                            authResult.user?.let { user -> trySend(Result.success(user)) }
                                ?: trySend(Result.failure(defaultErrorAuthException))
                        }?.addOnFailureListener { trySend(Result.failure(it)) }
                }.addOnFailureListener {
                    trySend(Result.failure(it))
                }
        } catch (e: Exception) {
            e.printStackTrace()
            trySend(Result.failure(e))
        } finally {
//            close()
        }
        awaitClose {
            Log.d(TAG, "closed register callback flow")
        }
    }
}