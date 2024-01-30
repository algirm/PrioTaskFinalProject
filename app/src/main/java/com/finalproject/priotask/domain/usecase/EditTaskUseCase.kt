package com.finalproject.priotask.domain.usecase

import com.finalproject.priotask.domain.model.Task
import com.finalproject.priotask.domain.repository.TaskRepository
import com.finalproject.priotask.util.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen

fun interface EditTaskUseCase : (Task) -> Flow<Result<Boolean>>

fun editTask(
    taskRepository: TaskRepository,
    task: Task
): Flow<Result<Boolean>> = taskRepository
    .editTask(task)
    .map { resultOf { it } }
    .retryWhen { cause, _ ->
        emit(Result.failure(cause))
        false
    }.catch { emit(Result.failure(it)) }