package ru.tanexc.notegraph.domain.interfaces.repository

import kotlinx.coroutines.flow.Flow
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.model.note.Note

interface NoteRepository {

    val notesFlow: Flow<List<Note>>

    fun getByUser(): Flow<Action<List<Note>>>

    fun getById(value: String): Flow<Action<Note?>>

    fun save(value: Note): Flow<Action<Note?>>

    fun delete(value: Note): Flow<Action<Unit>>

    fun update(value: Note): Flow<Action<Unit>>

}