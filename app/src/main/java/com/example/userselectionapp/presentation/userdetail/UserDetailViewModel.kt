package com.example.userselectionapp.presentation.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.userselectionapp.domain.model.User
import com.example.userselectionapp.domain.usecase.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel(){

    private val userId: Int = savedStateHandle["userId"] ?: -1

    private val _state = MutableStateFlow(UserDetailsState())
    val state = _state

    fun processIntent(intent: UserDetailsIntent){
        when(intent){
            is UserDetailsIntent.LoadUser -> loadUser()
        }
    }

    private fun loadUser() {
        _state.update { it.copy(user = getUserByIdUseCase(userId)) }
    }
}