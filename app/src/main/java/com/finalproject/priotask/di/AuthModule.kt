package com.finalproject.priotask.di

import com.finalproject.priotask.data.repository.AuthRepositoryImpl
import com.finalproject.priotask.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface AuthModule {
    
    @Binds
    @ViewModelScoped
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}