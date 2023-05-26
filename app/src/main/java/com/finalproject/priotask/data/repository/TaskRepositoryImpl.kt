package com.finalproject.priotask.data.repository

import android.util.Log
import com.finalproject.priotask.data.TaskDtoModel
import com.finalproject.priotask.data.toDomainModel
import com.finalproject.priotask.data.toDtoModel
import com.finalproject.priotask.domain.model.Task
import com.finalproject.priotask.domain.repository.TaskRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : TaskRepository {

    private val TAG = this.javaClass.simpleName

    override fun getTasks(): Flow<List<Task>> = callbackFlow {
        val result = firebaseFirestore
            .collection(firebaseAuth.currentUser!!.uid)
            .get()
            .await()

        val listTask = result.toObjects(TaskDtoModel::class.java).map { it.toDomainModel() }
        trySend(listTask)
        awaitClose {
            Log.d(TAG, "getTasks: closed callbackFlow")
        }
    }

    override fun addTask(task: Task): Flow<Result<Unit>> = callbackFlow {
        firebaseFirestore
            .collection(firebaseAuth.currentUser!!.uid)
            .document(task.id)
            .set(task.toDtoModel())
            .addOnSuccessListener {
                trySend(Result.success(Unit))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }

        awaitClose {
            Log.d(TAG, "addTask: closed callbackFlow")
        }
    }
}