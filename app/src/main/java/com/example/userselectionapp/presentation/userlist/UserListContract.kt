package com.example.userselectionapp.presentation.userlist

import com.example.userselectionapp.domain.model.User

sealed interface UserListIntent {
    object ScreenLoaded : UserListIntent

    data class SearchQueryChanged(
        val query: String
    ) : UserListIntent

    data class ToggleUserSelection(
        val userId: Int
    ) : UserListIntent

    data class UserClicked(
        val userId: Int
    ) : UserListIntent

    object SubmitClicked : UserListIntent
}

data class UserListState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val searchQuery: String = ""
)

sealed interface UserListEvent {
    data class ShowToast(val message: String): UserListEvent
    data class NavigationToUserDetail(val userId: Int) : UserListEvent
}