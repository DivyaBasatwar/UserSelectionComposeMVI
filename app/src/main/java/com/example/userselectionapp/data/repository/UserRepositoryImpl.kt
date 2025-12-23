package com.example.userselectionapp.data.repository

import com.example.userselectionapp.data.source.FakeDataSource
import com.example.userselectionapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val dataSource: FakeDataSource) : UserRepository {
    override fun getUsers() = flow {
        emit(dataSource.fetchUsers())
    }

}