package ru.tanexc.notegraph.data.firebase.dao

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await
import ru.tanexc.notegraph.domain.interfaces.dao.NoteDao
import ru.tanexc.notegraph.domain.model.Note
import javax.inject.Inject

class NoteDaoImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
): NoteDao {

    private val collection: CollectionReference = fireStore.collection("note")

    override suspend fun create(note: Note) {
        collection
            .add(note)
            .await()
    }

    override suspend fun getByUser(uid: String): List<Note> = collection
        .whereEqualTo("uid", uid)
        .get()
        .await()
        .toObjects()

    override suspend fun getById(documentId: String): Note = collection
        .whereEqualTo("documentId", documentId)
        .get()
        .await()
        .toObjects<Note>()
        .first()


    override suspend fun update(note: Note) {
        collection
            .document(note.documentId)
            .update(note.asMap())
            .await()
    }

    override suspend fun save(note: Note) {
        collection
            .document(note.documentId)
            .set(note)
            .await()
    }

    override suspend fun delete(note: Note) {
        collection
            .document(note.documentId)
            .delete()
            .await()
    }


}