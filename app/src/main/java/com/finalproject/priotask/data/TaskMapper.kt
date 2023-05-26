package com.finalproject.priotask.data

import com.finalproject.priotask.domain.model.Priority
import com.finalproject.priotask.domain.model.Task

fun Task.toDtoModel() = TaskDtoModel(
    id = this.id,
    name = this.name,
    description = this.description,
    priority = when (this.priority) {
        Priority.Common -> 0
        Priority.Important -> 1
    },
    deadline = this.deadline
)

fun TaskDtoModel.toDomainModel() = Task(
    id = this.id!!,
    name = this.name!!,
    description = this.description!!,
    priority = when (this.priority!!) {
        0 -> Priority.Common
        1 -> Priority.Important
        else -> Priority.Common
    },
    deadline = this.deadline!!
)