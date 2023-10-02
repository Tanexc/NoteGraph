package ru.tanexc.notegraph.data.firebase.dao

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await
import ru.tanexc.notegraph.data.firebase.entity.UserEntity
import ru.tanexc.notegraph.data.keys.Keys.LOCAL_USER_ID
import ru.tanexc.notegraph.domain.interfaces.dao.UserDao
import ru.tanexc.notegraph.domain.model.user.User
import javax.inject.Inject

class UserDaoImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val dataStore: DataStore<Preferences>
) : UserDao {
    private val collection: CollectionReference = fireStore.collection("user")

    override suspend fun create(user: User) {
        collection
            .document(user.uid)
            .set(user.asFirebaseEntity())
            .await()
        dataStore.edit {
            it[LOCAL_USER_ID] = user.uid
        }
    }

    override suspend fun signOut() {
        fireStore
            .clearPersistence()
        dataStore.edit {
            it[LOCAL_USER_ID] = ""
        }
    }

    override suspend fun getLocal(): User? = collection
        .get(Source.CACHE)
        .await()
        .toObjects<UserEntity>()
        .firstOrNull()
        ?.asDomain()

    override suspend fun getRemote(uid: String): User? = if (uid != "") {
        collection
            .document(uid)
            .get()
            .await()
            .toObject<UserEntity>()
            ?.apply {
                dataStore.edit {
                    it[LOCAL_USER_ID] = this.uid
                }
            }
            ?.asDomain()

    } else null

    override suspend fun update(user: User) {
        collection
            .document(user.uid)
            .update(user.asFirebaseEntity().asMap())
            .await()
    }

}