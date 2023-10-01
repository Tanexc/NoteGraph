package ru.tanexc.notegraph.domain.interfaces.dao

import kotlinx.coroutines.flow.Flow
import ru.tanexc.notegraph.domain.model.Note

interface NoteDao {
    suspend fun create(note: Note)

    suspend fun getByUser(): List<Note>

    suspend fun getById(documentId: String): Note?

    suspend fun update(note: Note)

    suspend fun save(note: Note)

    suspend fun delete(note: Note)

    fun getNotesFlow(): Flow<List<Note>>
}