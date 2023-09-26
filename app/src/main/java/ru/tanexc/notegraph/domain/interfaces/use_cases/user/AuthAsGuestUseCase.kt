package ru.tanexc.notegraph.domain.interfaces.use_cases.user

import ru.tanexc.notegraph.domain.interfaces.repository.AuthRepository
import javax.inject.Inject

class AuthAsGuestUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.authAsGuest()
}