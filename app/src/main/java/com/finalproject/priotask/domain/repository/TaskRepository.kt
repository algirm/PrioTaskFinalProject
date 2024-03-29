package com.finalproject.priotask.domain.repository

import com.finalproject.priotask.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    
    fun getTasks(forceRefresh: Boolean): Flow<List<Task>>

    fun addTask(task: Task): Flow<Boolean>
    
    fun editTask(task: Task): Flow<Boolean>
    
    fun deleteTask(id: String): Flow<Boolean>
    
}