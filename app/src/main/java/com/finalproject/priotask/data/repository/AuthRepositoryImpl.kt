package com.finalproject.priotask.data.repository

import android.util.Log
import com.finalproject.priotask.domain.model.User
import com.finalproject.priotask.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
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

    override fun login(email: String, password: String): Flow<Result<User>> = callbackFlow {
        try {
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    authResult.user?.let { trySend(Result.success(User(it.displayName!!, it.email!!))) }
                        ?: trySend(Result.failure(defaultErrorAuthException))
                    close()
                }.addOnFailureListener { e -> 
                    trySend(Result.failure(e))
                    close()
                }
        } catch (e: Exception) {
            e.printStackTrace()
            trySend(Result.failure(e))
            close()
        }
        awaitClose {
            Log.d(TAG, "closed login callback flow")
        }
    }

    override fun getUser(): User? {
        return try {
            val user = firebaseAuth.currentUser!!
            User(user.displayName!!, user.email!!)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun registerWithEmailAndPassword(fullName: String, email: String, password: String) = callbackFlow<Result<User>> {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    authResult.user
                        ?.updateProfile(userProfileChangeRequest { displayName = fullName })
                        ?.addOnSuccessListener {
                            authResult.user?.let { user -> 
                                trySend(Result.success(User(user.displayName!!, user.email!!))) 
                            } ?: trySend(Result.failure(defaultErrorAuthException))
                            close()
                        }?.addOnFailureListener { trySend(Result.failure(it)) }
                }.addOnFailureListener {
                    trySend(Result.failure(it))
                    close()
                }
        } catch (e: Exception) {
            e.printStackTrace()
            trySend(Result.failure(e))
            close()
        }
        awaitClose {
            Log.d(TAG, "closed register callback flow")
        }
    }
}