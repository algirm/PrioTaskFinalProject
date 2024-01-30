package com.finalproject.priotask.domain.usecase

import com.finalproject.priotask.domain.model.Task
import com.finalproject.priotask.domain.repository.TaskRepository
import com.finalproject.priotask.util.resultOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException


fun interface AddTaskUseCase : (Task) -> Flow<Result<Boolean>>

fun addTask(
    taskRepository: TaskRepository,
    task: Task
): Flow<Result<Boolean>> = taskRepository
    .addTask(task)
    .map { resultOf { it } }
    .retryWhen { cause, _ ->
        emit(Result.failure(cause))
        false
    }.catch { emit(Result.failure(it)) }