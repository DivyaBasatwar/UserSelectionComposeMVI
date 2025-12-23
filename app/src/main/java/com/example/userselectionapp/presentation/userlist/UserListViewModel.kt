package com.example.userselectionapp.presentation.userlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userselectionapp.domain.model.User
import com.example.userselectionapp.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserListUiState(isLoading = true))
    val uiState : StateFlow<UserListUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<String>()
    val events = _events.asSharedFlow()

    private var originalUsers: List<User> = emptyList()
    private val searchQuery = MutableStateFlow("")

    init {
        loadUsers()
        observeSearch()
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }
    private fun loadUsers() {
        viewModelScope.launch {
            getUsersUseCase()
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }
                .collect { users ->
                    originalUsers = users
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            users = users
                        )
                    }
                }
        }
    }

    private fun observeSearch() {
        viewModelScope.launch {
            searchQuery.debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    if(query.length < 3){
                        _uiState.update {
                            it.copy(
                                users = originalUsers
                            )
                        }
                    } else{
                        val filtered = originalUsers.filter {
                            it.name.contains(query, ignoreCase = true)
                        }
                        _uiState.update {
                            it.copy(
                                users = filtered
                            )
                        }
                    }

                }
        }
    }

    fun toggleUserSelection(userId: Int){
        _uiState.update { current ->
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

    fun submitSelectedUsers() {
        viewModelScope.launch {
            val selectedUsers = _uiState.value.users.filter { it.isSelected }

            selectedUsers.forEach { user ->
                Log.d(
                    "UserSubmit",
                    "Selected User -> id=${user.id}, name=${user.name}, email=${user.email}"
                )
            }

            _events.emit(
                "${selectedUsers.map { it.name }}"
            )
        }
    }

}