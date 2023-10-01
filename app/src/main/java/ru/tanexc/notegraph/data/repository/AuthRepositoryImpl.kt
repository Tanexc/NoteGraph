package ru.tanexc.notegraph.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.tanexc.notegraph.domain.interfaces.dao.UserDao
import ru.tanexc.notegraph.domain.interfaces.repository.AuthRepository
import ru.tanexc.notegraph.domain.model.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val userDao: UserDao
) : AuthRepository {

    override val userFlow: Flow<User?>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                CoroutineScope(coroutineContext).launch {
                    val user = userDao.getRemote(auth.currentUser?.uid ?: "")
                    trySend(user)
                }
            }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun tryGetLocalUser(): User? = userDao.getLocal()

    override suspend fun authByEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await().user

    }

    override suspend fun authAsGuest() {
        val user = auth.signInAnonymously().await().user
        user?.let {
            userDao.create(
                User(
                    uid = it.uid,
                    email = "",
                    name = "Guest"
                )
            )
        }
    }

    override suspend fun authByGoogle() {
        TODO("google auth")
    }

    override suspend fun signOut() {
        if (auth.currentUser?.isAnonymous == true) {
            deleteAccount()
        }
        auth.signOut()
        userDao.signOut()
    }

    override fun deleteAccount() {
        auth.currentUser!!.delete()
    }

    override suspend fun signUp(email: String, password: String, name: String) {
        val user: FirebaseUser? = auth.createUserWithEmailAndPassword(email, password).await().user

        user?.let {
            userDao.create(
                User(
                    uid = it.uid,
                    email = email,
                    name = name
                )
            )
        }
    }

}