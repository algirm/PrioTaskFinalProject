package com.finalproject.priotask.data

import com.finalproject.priotask.domain.model.Priority
import java.util.*

data class TaskDtoModel(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val priority: Int? = null,
    val deadline: Date? = null
)
