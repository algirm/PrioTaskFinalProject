package com.finalproject.priotask.domain.usecase

import com.finalproject.priotask.domain.repository.TaskRepository
import com.finalproject.priotask.util.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen


fun interface DoneTaskUseCase : (String) -> Flow<Result<Boolean>>

fun doneTask(
    taskRepository: TaskRepository,
    id: String
): Flow<Result<Boolean>> = taskRepository
    .deleteTask(id)
    .map { resultOf { it } }
    .retryWhen { cause, _ ->
        emit(Result.failure(cause))
        false
    }.catch { emit(Result.failure(it)) }