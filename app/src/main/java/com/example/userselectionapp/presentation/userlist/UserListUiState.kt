package com.example.userselectionapp.presentation.userlist

import com.example.userselectionapp.domain.model.User

data class UserListUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val searchQuery: String = ""
)
