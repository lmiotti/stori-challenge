package com.stori.challenge.domain.usecase

import com.stori.challenge.data.network.model.Profile
import com.stori.challenge.data.network.repository.auth.AuthRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(): Profile {
        return authRepository.getProfile()
    }
}