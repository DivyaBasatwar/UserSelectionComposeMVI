package com.example.userselectionapp.di

import com.example.userselectionapp.data.repository.UserRepositoryImpl
import com.example.userselectionapp.data.source.FakeDataSource
import com.example.userselectionapp.domain.repository.UserRepository
import com.example.userselectionapp.domain.usecase.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFakeDataSource(): FakeDataSource {
        return FakeDataSource()
    }

    @Provides
    @Singleton
    fun provideUserRepository(dataSource: FakeDataSource): UserRepository {
        return UserRepositoryImpl(dataSource)
    }

    @Provides
    fun provideGetUsersUseCase(repository: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(repository)
    }

}