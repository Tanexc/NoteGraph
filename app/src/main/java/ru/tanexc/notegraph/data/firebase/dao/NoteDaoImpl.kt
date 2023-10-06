package ru.tanexc.notegraph.data.firebase.dao

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.core.ListenerRegistrationImpl
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import ru.tanexc.notegraph.data.firebase.entity.NoteEntity
import ru.tanexc.notegraph.data.keys.Keys.LOCAL_USER_ID
import ru.tanexc.notegraph.domain.interfaces.dao.NoteDao
import ru.tanexc.notegraph.domain.model.note.Note
import javax.inject.Inject

class NoteDaoImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val dataStore: DataStore<Preferences>
) : NoteDao {
    override suspend fun create(note: Note) {
        val noteDocument = fireStore
            .collection("user")
            .document(dataStore.data.first()[LOCAL_USER_ID] ?: "")
            .collection("notes")
            .document()

        noteDocument
            .set(note.asFirebaseEntity().copy(documentId = noteDocument.id))
            .await()
    }

    override suspend fun getByUser(): List<Note> = fireStore
        .collection("user")
        .document(dataStore.data.first()[LOCAL_USER_ID] ?: "")
        .collection("notes")
        .get()
        .await()
        .toObjects<NoteEntity>()
        .map { it.asDomain() }

    override suspend fun getById(documentId: String): Note? = fireStore
        .collection("user")
        .document(dataStore.data.first()[LOCAL_USER_ID] ?: "")
        .collection("notes")
        .whereEqualTo("documentId", documentId)
        .get()
        .await()
        .toObjects<NoteEntity>()
        .firstOrNull()
        ?.asDomain()


    override suspend fun update(note: Note) {
        val user = fireStore
            .collection("user")
            .document(dataStore.data.first()[LOCAL_USER_ID] ?: "")
        user.collection("notes")
            .document(note.documentId)
            .update(note.asFirebaseEntity().asMap())
            .await()
    }

    override suspend fun save(note: Note): Note? =
        fireStore
            .collection("user")
            .document(dataStore.data.first()[LOCAL_USER_ID] ?: "")
            .collection("notes")
            .document()
            .apply {
                this
                    .set(note.asFirebaseEntity().copy(documentId = this.id))
                    .await()
            }
            .get()
            .await()
            .toObject<NoteEntity>()
            ?.asDomain()


    override suspend fun delete(note: Note) {
        fireStore
            .collection("user")
            .document(dataStore.data.first()[LOCAL_USER_ID] ?: "")
            .collection("notes")
            .document(note.documentId)
            .delete()
            .await()
    }

    override fun getNotesFlow(): Flow<List<Note>> = callbackFlow {
        dataStore.data.first()[LOCAL_USER_ID]?.let { id ->
            val notes = fireStore
                .collection("user")
                .document(id)
                .collection("notes")
                .addSnapshotListener { value, error ->
                    trySend(value?.toObjects<NoteEntity>()?.map { it.asDomain() } ?: emptyList())
                }
            awaitClose { notes.remove() }
        }
        awaitClose {  }

    }

}