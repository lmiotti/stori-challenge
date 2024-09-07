package com.stori.challenge.di

import com.stori.challenge.data.network.utils.SessionManager
import com.stori.challenge.data.network.utils.SessionManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    abstract fun bindSessionManager(sessionManagerImpl: SessionManagerImpl): SessionManager
}