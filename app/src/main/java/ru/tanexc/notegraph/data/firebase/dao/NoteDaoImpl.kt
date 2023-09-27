package ru.tanexc.notegraph.data.firebase.dao

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ru.tanexc.notegraph.domain.interfaces.dao.NoteDao
import ru.tanexc.notegraph.domain.model.Note
import javax.inject.Inject

class NoteDaoImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
): NoteDao {
    override suspend fun create(note: Note) {
        fireStore
            .collection("user")
            .document(auth.uid?:"")
            .collection("notes")
            .add(note)
            .await()
    }

    override suspend fun getByUser(uid: String): List<Note> = fireStore
        .collection("user")
        .document(uid)
        .collection("notes")
        .get()
        .await()
        .toObjects()

    override suspend fun getById(documentId: String): Note? = fireStore
        .collection("user")
        .document(auth.uid?:"")
        .collection("notes")
        .whereEqualTo("documentId", documentId)
        .get()
        .await()
        .toObjects<Note>()
        .firstOrNull()



    override suspend fun update(note: Note) {
        fireStore
            .collection("user")
            .document(auth.uid?:"")
            .collection("notes")
            .document(note.documentId)
            .update(note.asMap())
            .await()
    }

    override suspend fun save(note: Note) {
        fireStore
            .collection("user")
            .document(auth.uid?:"")
            .collection("notes")
            .document(note.documentId)
            .set(note)
            .await()
    }

    override suspend fun delete(note: Note) {
        fireStore
            .collection("user")
            .document(auth.uid?:"")
            .collection("notes")
            .document(note.documentId)
            .delete()
            .await()
    }

    override fun getNotesFlow(): Flow<List<Note>> = callbackFlow {
        fireStore
            .collection("user")
            .document(auth.uid?:"")
            .collection("notes")
            .addSnapshotListener { value, error ->
                trySend(value?.toObjects()?: emptyList())
            }
    }

}