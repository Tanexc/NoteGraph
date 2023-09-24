package ru.tanexc.notegraph.data.repository

import kotlinx.coroutines.flow.Flow
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.interfaces.repository.NoteRepository
import ru.tanexc.notegraph.domain.model.Note

class NoteRepositoryImpl: NoteRepository {
    override suspend fun getByUserId(value: String): Flow<Action<Note>> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(value: Long): Flow<Action<Note>> {
        TODO("Not yet implemented")
    }

    override suspend fun save(value: Note): Flow<Action<Note>> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(value: Note): Flow<Action<Note>> {
        TODO("Not yet implemented")
    }

    override suspend fun update(value: Note): Flow<Action<Note>> {
        TODO("Not yet implemented")
    }
}