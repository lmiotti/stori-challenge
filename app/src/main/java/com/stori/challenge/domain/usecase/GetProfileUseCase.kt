package com.stori.challenge.domain.usecase

import com.stori.challenge.data.network.model.Profile
import com.stori.challenge.data.network.repository.auth.AuthRepository
import com.stori.challenge.data.network.repository.profile.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    operator fun invoke(): Profile {
        return repository.getProfile()
    }
}