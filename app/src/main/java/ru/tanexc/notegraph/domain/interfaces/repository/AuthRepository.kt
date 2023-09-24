package ru.tanexc.notegraph.domain.interfaces.repository

interface AuthRepository {

    val userId: String

    suspend fun authByEmail(email: String, password: String)

    suspend fun authAsGuest()

    suspend fun authByGoogle()

    suspend fun signOut()

    fun deleteAccount()

    suspend fun signUp(email: String, password: String, name: String)
}