package com.finalproject.priotask.data

import com.finalproject.priotask.domain.model.Priority
import com.finalproject.priotask.domain.model.Task

fun Task.toDtoModel() = TaskDtoModel(
    id = this.id.ifBlank { null },
    name = this.name,
    description = this.description,
    priority = when (this.priority) {
        Priority.Low -> 0
        Priority.Moderate -> 1
        Priority.High -> 2
    },
    deadline = this.deadline,
    createdAt = this.createdAt
)

fun TaskDtoModel.toDomainModel() = Task(
    id = this.id!!,
    name = this.name!!,
    description = this.description!!,
    priority = when (this.priority!!) {
        0 -> Priority.Low
        1 -> Priority.Moderate
        2 -> Priority.High
        else -> Priority.Low
    },
    deadline = this.deadline!!,
    createdAt = this.createdAt!!
)