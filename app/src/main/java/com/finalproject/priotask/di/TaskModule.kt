package com.finalproject.priotask.di

import com.finalproject.priotask.data.repository.TaskRepositoryImpl
import com.finalproject.priotask.domain.repository.TaskRepository
import com.finalproject.priotask.domain.usecase.GetTasksUseCase
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
        return GetTasksUseCase { getTasks(taskRepository) }
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface TaskBindModule {
    
    @Binds
    @ViewModelScoped
    fun bindTaskRepository(taskRepositoryImpl: TaskRepositoryImpl): TaskRepository
    
}