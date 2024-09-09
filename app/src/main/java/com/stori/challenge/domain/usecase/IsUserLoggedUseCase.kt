package com.stori.challenge.domain.usecase

import com.stori.challenge.data.network.repository.auth.AuthRepository
import javax.inject.Inject

class IsUserLoggedUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(): Boolean = repository.isUserLogged
}