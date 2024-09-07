package com.stori.challenge.di

import com.stori.challenge.data.network.repository.auth.AuthRepository
import com.stori.challenge.data.network.repository.auth.AuthRepositoryImpl
import com.stori.challenge.data.network.repository.movements.MovementsRepository
import com.stori.challenge.data.network.repository.movements.MovementsRepositoryImpl
import com.stori.challenge.data.network.repository.profile.ProfileRepository
import com.stori.challenge.data.network.repository.profile.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindMovementsRepository(movementsRepositoryImpl: MovementsRepositoryImpl): MovementsRepository

    @Binds
    abstract fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository
}