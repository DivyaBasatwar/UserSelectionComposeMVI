package com.example.userselectionapp.presentation.userlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userselectionapp.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UserListState())
    val state : StateFlow<UserListState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<UserListEvent>()
    val events = _events.asSharedFlow()

    fun processIntent(intent: UserListIntent){
        when(intent){
            is UserListIntent.ScreenLoaded -> loadUsers()
            is UserListIntent.SearchQueryChanged -> updateSearch(intent.query)
            is UserListIntent.ToggleUserSelection -> toggleUserSelection(intent.userId)
            is UserListIntent.UserClicked -> openUserDetail(intent.userId)
            is UserListIntent.SubmitClicked -> submitSelectedUsers()
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            getUsersUseCase()
                .onStart {
                    _state.update { it.copy(isLoading = true) }
                }
                .collect { users ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            users = users
                        )
                    }
                }
        }
    }

    private fun updateSearch(query: String){
        _state.update {
            it.copy(searchQuery = query)
        }
    }

    private fun toggleUserSelection(userId: Int){
        _state.update { current ->
            current.copy(
                users = current.users.map { user ->
                    if(user.id == userId){
                        user.copy(isSelected = !user.isSelected)
                    }
                    else user
                }
            )
        }
    }

    private fun openUserDetail(userId: Int){
        viewModelScope.launch {
            _events.emit(
                UserListEvent.NavigationToUserDetail(userId)
            )
        }
    }

    private fun submitSelectedUsers() {
        viewModelScope.launch {
            val selectedUsers = _state.value.users.filter { it.isSelected }

            selectedUsers.forEach { user ->
                Log.d(
                    "UserSubmit",
                    "Selected User -> id=${user.id}, name=${user.name}, email=${user.email}"
                )
            }

            _events.emit(
                UserListEvent.ShowToast(
                    "${selectedUsers.map { it.name }}"
                )
            )
        }
    }

}