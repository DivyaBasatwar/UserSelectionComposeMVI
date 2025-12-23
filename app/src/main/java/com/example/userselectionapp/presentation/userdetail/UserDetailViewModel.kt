package com.example.userselectionapp.presentation.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.userselectionapp.domain.model.User
import com.example.userselectionapp.domain.usecase.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel(){

    private val userId: Int = savedStateHandle["userId"] ?: -1
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        _user.value = getUserByIdUseCase(userId)
    }
}