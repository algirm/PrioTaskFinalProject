package com.finalproject.priotask.domain.model

sealed class Priority {
    object Important: Priority()
    object Common: Priority()
}