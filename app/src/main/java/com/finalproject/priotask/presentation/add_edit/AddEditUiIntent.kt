package com.finalproject.priotask.presentation.add_edit

import com.finalproject.priotask.domain.model.Task

sealed class AddEditUiIntent {
    data class AddTask(val task: Task) : AddEditUiIntent()
    data class DoneEditTask(val task: Task) : AddEditUiIntent()
    data class DoneTask(val id: String) : AddEditUiIntent()
}
