package com.finalproject.priotask.domain.repository

import com.finalproject.priotask.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    
    fun getTasks(): Flow<List<Task>>

    fun addTask(task: Task): Flow<Result<Unit>>
}