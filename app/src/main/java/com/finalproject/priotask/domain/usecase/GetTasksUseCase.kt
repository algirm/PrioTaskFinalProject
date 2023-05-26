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

private const val RETRY_DELAY_MILLIS = 10_000L

fun interface GetTasksUseCase : () -> Flow<Result<List<Task>>>

fun getTasks(
    taskRepository: TaskRepository
): Flow<Result<List<Task>>> = taskRepository
    .getTasks()
    .map { resultOf { it } }
    .retryWhen { cause, _ ->
        if (cause is IOException) {
            emit(Result.failure(cause))
            delay(RETRY_DELAY_MILLIS)
            true
        } else false
    }.catch { emit(Result.failure(it)) }