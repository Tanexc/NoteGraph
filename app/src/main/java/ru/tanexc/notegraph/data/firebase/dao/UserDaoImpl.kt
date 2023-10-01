package ru.tanexc.notegraph.data.firebase.dao

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await
import ru.tanexc.notegraph.domain.interfaces.dao.UserDao
import ru.tanexc.notegraph.domain.model.User
import javax.inject.Inject

class UserDaoImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
) : UserDao {
    private val collection: CollectionReference = fireStore.collection("user")

    override suspend fun create(user: User) {
        collection
            .document(user.uid)
            .set(user)
            .await()
    }

    override suspend fun signOut() {
        fireStore
            .clearPersistence()
    }

    override suspend fun getLocal(): User? = collection
        .get(Source.CACHE)
        .await()
        .toObjects<User>()
        .firstOrNull()

    override suspend fun getRemote(uid: String): User? = if (uid != "") {
        collection
            .document(uid)
            .get()
            .await()
            .toObject()
    } else null

    override suspend fun update(user: User) {
        collection
            .document(user.uid)
            .update(user.asMap())
            .await()
    }

}