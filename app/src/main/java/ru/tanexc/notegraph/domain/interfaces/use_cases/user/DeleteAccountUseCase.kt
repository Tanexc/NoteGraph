package ru.tanexc.notegraph.domain.interfaces.use_cases.user

import ru.tanexc.notegraph.domain.interfaces.repository.AuthRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) = repository.authByEmail(email, password)
}