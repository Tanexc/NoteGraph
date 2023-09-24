package ru.tanexc.notegraph.data.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import ru.tanexc.notegraph.domain.interfaces.dao.UserDao
import ru.tanexc.notegraph.domain.interfaces.repository.AuthRepository
import ru.tanexc.notegraph.domain.model.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val userDao: UserDao,
) : AuthRepository {

    private val _userId: MutableState<String> = mutableStateOf(auth.currentUser?.uid.orEmpty())
    override val userId: String by _userId

    val userFlow: Flow<User> = flow {
        val user = userDao.getLocal(userId)?: userDao.getRemote(userId)
        user?.let {
            emit(user)
        }
    }

    override suspend fun authByEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
        _userId.value = auth.currentUser?.uid.orEmpty()
    }

    override suspend fun authAsGuest() {
        auth.signInAnonymously().await()
        _userId.value = auth.currentUser?.uid.orEmpty()
    }

    override suspend fun authByGoogle() {
        TODO("google auth")
        /*
            val oneTapClient = Identity.getSignInClient()
            val account = GoogleSignIn.getSignedInAccountFromIntent(null).result!!
            val googleCredential = GoogleAuthProvider.getCredential(account.idToken, null)

            auth.signInWithCredential(googleCredential)
            _userId.value = auth.currentUser?.uid.orEmpty()*/
    }

    override suspend fun signOut() {
        if (auth.currentUser!!.isAnonymous) {
            deleteAccount()
        }
        auth.signOut()
    }

    override fun deleteAccount() {
        auth.currentUser!!.delete()
    }

    override suspend fun signUp(email: String, password: String, name: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        val user: FirebaseUser? = auth.currentUser!!.linkWithCredential(credential).await().user
        user?.let {
            userDao.create(
                User(
                    uid = it.uid,
                    email = email,
                    name = name
                )
            )
            _userId.value = it.uid
        }
    }
}