package ru.tanexc.notegraph.domain.interfaces.repository

import kotlinx.coroutines.flow.Flow
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.model.note.ImagePiece
import ru.tanexc.notegraph.domain.model.note.Note
import ru.tanexc.notegraph.domain.model.note.TextPiece

interface NoteRepository {

    val notesFlow: Flow<List<Note>>

    fun getByUser(): Flow<Action<List<Note>>>

    fun getById(value: String): Flow<Action<Note?>>

    fun save(value: Note): Flow<Action<Note?>>

    fun delete(value: Note): Flow<Action<Unit>>

    fun update(value: Note): Flow<Action<Unit>>

    fun updateImagePiece(noteId: String, value: ImagePiece): Flow<Action<Unit>>

    fun updateTextPiece(noteId: String, value: TextPiece): Flow<Action<Unit>>

    fun deleteImagePiece(noteId: String, value: ImagePiece): Flow<Action<Unit>>

    fun deleteTextPiece(noteId: String, value: TextPiece): Flow<Action<Unit>>
}