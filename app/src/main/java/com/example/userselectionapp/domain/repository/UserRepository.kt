package com.example.userselectionapp.domain.repository

import com.example.userselectionapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<User>>
}