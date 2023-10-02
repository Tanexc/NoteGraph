package ru.tanexc.notegraph.domain.interfaces.repository

import kotlinx.coroutines.flow.Flow
import ru.tanexc.notegraph.domain.model.user.User

interface AuthRepository {

    val userFlow: Flow<User?>

    suspend fun tryGetLocalUser(): User?

    suspend fun authByEmail(email: String, password: String)

    suspend fun authAsGuest()

    suspend fun authByGoogle()

    suspend fun signOut()

    fun deleteAccount()

    suspend fun signUp(email: String, password: String, name: String)
}