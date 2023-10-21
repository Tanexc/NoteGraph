package ru.tanexc.notegraph.data.firebase.dao

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.core.ListenerRegistrationImpl
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.tanexc.notegraph.data.firebase.entity.ImagePieceEntity
import ru.tanexc.notegraph.data.firebase.entity.NoteEntity
import ru.tanexc.notegraph.data.firebase.entity.TextPieceEntity
import ru.tanexc.notegraph.data.keys.Keys.LOCAL_USER_ID
import ru.tanexc.notegraph.domain.interfaces.dao.NoteDao
import ru.tanexc.notegraph.domain.model.note.ImagePiece
import ru.tanexc.notegraph.domain.model.note.Note
import ru.tanexc.notegraph.domain.model.note.TextPiece
import javax.inject.Inject

class NoteDaoImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val dataStore: DataStore<Preferences>
) : NoteDao {

    override suspend fun getByUser(): List<Note> {
        val noteCollection = fireStore.collection("user")
            .document(dataStore.data.first()[LOCAL_USER_ID] ?: "")
            .collection("notes")
        return noteCollection.get()
            .await()
            .toObjects<NoteEntity>()
            .map { note ->
                val imagePiece = noteCollection
                    .document(note.documentId)
                    .collection("image_pieces")
                    .get()
                    .await()
                    .toObjects<ImagePieceEntity>()
                    .map { it.asDomain() }

                val textPieces = noteCollection
                    .document(note.documentId)
                    .collection("text_pieces")
                    .get()
                    .await()
                    .toObjects<TextPieceEntity>()
                    .map { it.asDomain() }

                note.asDomain().copy(imagePieces = imagePiece, textPieces = textPieces)
            }
    }

    override suspend fun getById(documentId: String): Note? {
        val note = fireStore
            .collection("user")
            .document(dataStore.data.first()[LOCAL_USER_ID] ?: "")
            .collection("notes")

        return note.whereEqualTo("documentId", documentId)
            .get()
            .await()
            .toObjects<NoteEntity>()
            .firstOrNull()
            ?.run {
                val imagePieces = note
                    .document(documentId)
                    .collection("image_pieces")
                    .get()
                    .await()
                    .toObjects<ImagePieceEntity>()
                    .map { it.asDomain() }

                val textPieces = note
                    .document(documentId)
                    .collection("text_pieces")
                    .get()
                    .await()
                    .toObjects<TextPieceEntity>()
                    .map { it.asDomain() }

                this.asDomain().copy(imagePieces = imagePieces, textPieces = textPieces)
            }
    }


    override suspend fun save(note: Note) {
        val user = fireStore
            .collection("user")
            .document(dataStore.data.first()[LOCAL_USER_ID] ?: "")
        user.collection("notes")
            .document(note.documentId)
            .update(note.asFirebaseEntity().asMap())
            .await()
    }

    override suspend fun create(): Note? {
        val user = fireStore
            .collection("user")
            .document(dataStore.data.first()[LOCAL_USER_ID] ?: "")
        val document = user
            .collection("notes")
            .document()
        user.collection("notes")
            .document(document.id)
            .set(Note.Empty().asFirebaseEntity().asMap())
            .await()
        return user.collection("notes")
            .document(document.id)
            .get()
            .await()
            .toObject()
    }

    override suspend fun updateImagePiece(noteId: String, imagePiece: ImagePiece) {
        val note = fireStore
            .collection("user")
            .document(dataStore.data.first()[LOCAL_USER_ID] ?: "")
            .collection("notes")
            .document(noteId)

        note
            .collection("image_pieces")
            .document(imagePiece.documentId)
            .set(imagePiece.asFirebaseEntity())
            .await()
    }

    override suspend fun updateTextPiece(noteId: String, textPiece: TextPiece) {
        val note = fireStore
            .collection("user")
            .document(dataStore.data.first()[LOCAL_USER_ID] ?: "")
            .collection("notes")
            .document(noteId)

        note
            .collection("text_pieces")
            .document(textPiece.documentId)
            .set(textPiece.asFirebaseEntity())
            .await()
    }

    override suspend fun deleteImagePiece(noteId: String, imagePiece: ImagePiece) {
        val note = fireStore
            .collection("user")
            .document(dataStore.data.first()[LOCAL_USER_ID] ?: "")
            .collection("notes")
            .document(noteId)

        note
            .collection("image_pieces")
            .document(imagePiece.documentId)
            .delete()
            .await()
    }

    override suspend fun deleteTextPiece(noteId: String, textPiece: TextPiece) {
        val note = fireStore
            .collection("user")
            .document(dataStore.data.first()[LOCAL_USER_ID] ?: "")
            .collection("notes")
            .document(noteId)

        note
            .collection("text_pieces")
            .document(textPiece.documentId)
            .delete()
            .await()
    }

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
            val listener = fireStore
                .collection("user")
                .document(id)
                .collection("notes")
                .addSnapshotListener { value, error ->
                    CoroutineScope(this.coroutineContext).launch {
                        val notes = getByUser()
                        trySend(notes)
                    }
                }
            awaitClose { listener.remove() }
        }
        awaitClose {  }

    }

    override fun getNoteAsFlow(noteId: String): Flow<Note> = callbackFlow {
        dataStore.data.first()[LOCAL_USER_ID]?.let { id ->
            val document = fireStore
                .collection("user")
                .document(id)
                .collection("notes")
                .document(noteId)
            val listener = document
                .addSnapshotListener { value, error ->
                    CoroutineScope(this.coroutineContext).launch {
                        getById(noteId)?.let {
                            trySend(it)
                        }
                    }
                }
            val note = document.get().await().toObject<Note>()
            note?.let {
                trySend(it)
            }
            awaitClose { listener.remove() }
        }
        awaitClose {  }
    }

}