package ru.tanexc.notegraph.domain.interfaces.repository

import kotlinx.coroutines.flow.Flow
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.model.Note

interface NoteRepository {

    val notesFlow: Flow<List<Note>>

    suspend fun getByUser(): Flow<Action<List<Note>>>

    suspend fun getById(value: String): Flow<Action<Note?>>

    suspend fun save(value: Note): Flow<Action<Unit>>

    suspend fun delete(value: Note): Flow<Action<Unit>>

    suspend fun update(value: Note): Flow<Action<Unit>>

}