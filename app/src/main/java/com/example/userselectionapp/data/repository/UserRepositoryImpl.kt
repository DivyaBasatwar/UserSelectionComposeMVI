package com.example.userselectionapp.data.repository

import com.example.userselectionapp.data.source.FakeDataSource
import com.example.userselectionapp.domain.model.User
import com.example.userselectionapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val dataSource: FakeDataSource) : UserRepository {
    override fun getUsers() = flow {
        emit(dataSource.fetchUsers())
    }

    override fun getUserById(userId: Int): User? {
        return dataSource.getUserById(userId)
    }
}