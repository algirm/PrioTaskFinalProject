package com.finalproject.priotask

import com.finalproject.priotask.common.BaseViewModel
import com.finalproject.priotask.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    fun checkUserLogin() = authRepository.getUser()
    
}