package com.stori.challenge.di

import com.stori.challenge.data.network.datasource.auth.AuthRemoteDataSource
import com.stori.challenge.data.network.datasource.auth.AuthRemoteDataSourceImpl
import com.stori.challenge.data.network.datasource.movements.MovementsRemoteDataSource
import com.stori.challenge.data.network.datasource.movements.MovementsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    abstract fun bindMovementsRemoteDataSource(movementsRemoteDataSourceImpl: MovementsRemoteDataSourceImpl): MovementsRemoteDataSource
}