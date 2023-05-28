package com.finalproject.priotask.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Task(
    val id: String,
    val name: String,
    val description: String,
    val priority: Priority,
    val deadline: Date,
    val createdAt: Date
) : Parcelable
