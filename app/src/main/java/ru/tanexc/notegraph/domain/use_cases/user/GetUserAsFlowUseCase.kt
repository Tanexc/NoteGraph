package ru.tanexc.notegraph.domain.use_cases.user

import kotlinx.coroutines.flow.Flow
import ru.tanexc.notegraph.domain.interfaces.repository.AuthRepository
import ru.tanexc.notegraph.domain.model.user.User
import javax.inject.Inject

class GetUserAsFlowUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<User?> = repository.userFlow
}