package ru.tanexc.notegraph.domain.interfaces.dao


import ru.tanexc.notegraph.domain.model.User

interface UserDao {
    suspend fun create(user: User)

    suspend fun getLocal(uid: String): User?

    suspend fun getRemote(uid: String): User?

    suspend fun update(user: User)
}