package com.finalproject.priotask.di

import com.finalproject.priotask.data.repository.TaskRepositoryImpl
import com.finalproject.priotask.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface TaskModule {
    
    @Binds
    @ViewModelScoped
    fun bindTaskRepository(taskRepositoryImpl: TaskRepositoryImpl): TaskRepository
}