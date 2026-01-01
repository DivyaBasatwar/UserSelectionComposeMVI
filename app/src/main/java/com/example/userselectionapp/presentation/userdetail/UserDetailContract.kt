package com.example.userselectionapp.presentation.userdetail

import com.example.userselectionapp.domain.model.User

sealed interface UserDetailsIntent {
    object LoadUser : UserDetailsIntent
}

data class UserDetailsState(
    var user: User? = null
)