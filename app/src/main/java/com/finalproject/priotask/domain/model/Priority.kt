package com.finalproject.priotask.domain.model

sealed class Priority {
    object High : Priority()
    object Moderate : Priority()
    object Low : Priority()
}