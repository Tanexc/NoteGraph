package ru.tanexc.notegraph.domain.interfaces.dao

import kotlinx.coroutines.flow.Flow
import ru.tanexc.notegraph.domain.model.note.ImagePiece
import ru.tanexc.notegraph.domain.model.note.Note
import ru.tanexc.notegraph.domain.model.note.TextPiece

interface NoteDao {

    suspend fun getByUser(): List<Note>

    suspend fun getById(documentId: String): Note?

    suspend fun create(): Note?

    suspend fun save(note: Note)

    suspend fun updateImagePiece(noteId: String, imagePiece: ImagePiece)

    suspend fun updateTextPiece(noteId: String, textPiece: TextPiece)

    suspend fun deleteImagePiece(noteId: String, imagePiece: ImagePiece)

    suspend fun deleteTextPiece(noteId: String, textPiece: TextPiece)

    suspend fun delete(note: Note)

    fun getNotesFlow(): Flow<List<Note>>

    fun getNoteAsFlow(noteId: String): Flow<Note>

    fun getPiecesAsFlow(noteId: String): Flow<Note>
}