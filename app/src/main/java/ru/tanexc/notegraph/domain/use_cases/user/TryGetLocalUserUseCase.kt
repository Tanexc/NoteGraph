package ru.tanexc.notegraph.domain.use_cases.user

import ru.tanexc.notegraph.domain.interfaces.repository.AuthRepository
import ru.tanexc.notegraph.domain.model.User
import javax.inject.Inject

class TryGetLocalUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(): User? = repository.tryGetLocalUser()

}