package com.finalproject.priotask.data

import com.finalproject.priotask.domain.model.Priority
import com.finalproject.priotask.domain.model.Task

fun Task.toDto() = TaskDtoModel(
    id = this.id,
    name = this.name,
    description = this.description,
    priority = when (this.priority) {
        Priority.Common -> 0
        Priority.Important -> 1
    },
    deadline = this.deadline
)