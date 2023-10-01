package ru.tanexc.notegraph.domain.use_cases.user

import ru.tanexc.notegraph.domain.interfaces.repository.AuthRepository
import javax.inject.Inject

class SignOutUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.signOut()
}