package com.finalproject.priotask.di

import com.finalproject.priotask.data.repository.TaskRepositoryImpl
import com.finalproject.priotask.domain.repository.TaskRepository
import com.finalproject.priotask.domain.usecase.AddTaskUseCase
import com.finalproject.priotask.domain.usecase.DoneTaskUseCase
import com.finalproject.priotask.domain.usecase.EditTaskUseCase
import com.finalproject.priotask.domain.usecase.GetTasksUseCase
import com.finalproject.priotask.domain.usecase.addTask
import com.finalproject.priotask.domain.usecase.doneTask
import com.finalproject.priotask.domain.usecase.editTask
import com.finalproject.priotask.domain.usecase.getTasks
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TaskModule {
    
    @Provides
    @ViewModelScoped
    fun provideGetTasksUseCase(taskRepository: TaskRepository): GetTasksUseCase {
        return GetTasksUseCase { getTasks(taskRepository, it) }
    }

    @Provides
    @ViewModelScoped
    fun provideAddTasksUseCase(taskRepository: TaskRepository): AddTaskUseCase {
        return AddTaskUseCase { addTask(taskRepository, it) }
    }

    @Provides
    @ViewModelScoped
    fun provideEditTasksUseCase(taskRepository: TaskRepository): EditTaskUseCase {
        return EditTaskUseCase { editTask(taskRepository, it) }
    }

    @Provides
    @ViewModelScoped
    fun provideDoneTasksUseCase(taskRepository: TaskRepository): DoneTaskUseCase {
        return DoneTaskUseCase { doneTask(taskRepository, it) }
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface TaskBindModule {
    
    @Binds
    @ViewModelScoped
    fun bindTaskRepository(taskRepositoryImpl: TaskRepositoryImpl): TaskRepository
    
}