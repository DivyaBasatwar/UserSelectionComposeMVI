package com.example.userselectionapp.domain.usecase

import com.example.userselectionapp.domain.repository.UserRepository

class GetUsersUseCase(private val repository: UserRepository) {
    operator fun invoke() = repository.getUsers()
}