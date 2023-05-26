package com.finalproject.priotask.data

import com.finalproject.priotask.domain.model.Priority
import java.util.*

data class TaskDtoModel(
    val id: String,
    val name: String,
    val description: String,
    val priority: Int,
    val deadline: Date
)
