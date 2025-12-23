package com.example.userselectionapp.domain.usecase

import com.example.userselectionapp.domain.model.User
import com.example.userselectionapp.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userId: Int): User? {
        return repository.getUserById(userId)
    }
}