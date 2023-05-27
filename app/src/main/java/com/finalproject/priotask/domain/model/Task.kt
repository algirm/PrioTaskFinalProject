package com.finalproject.priotask.domain.model

import java.util.Date

data class Task(
    val id: String,
    val name: String,
    val description: String,
    val priority: Priority,
    val deadline: Date,
    val createdAt: Date
)
