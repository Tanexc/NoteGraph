package ru.tanexc.notegraph.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.interfaces.dao.NoteDao
import ru.tanexc.notegraph.domain.interfaces.repository.NoteRepository
import ru.tanexc.notegraph.domain.model.Note
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
): NoteRepository {
    override suspend fun getByUserId(value: String): Flow<Action<List<Note>>> = flow {
        emit(Action.Loading(emptyList()))
        runCatching {
            val data = noteDao.getByUser(value)
            emit(Action.Success(data))
        }.onFailure { exception ->
            emit(Action.Error(emptyList(), messsage = exception.message))
        }
    }

    override suspend fun getById(value: String): Flow<Action<Note>> = flow {
        emit(Action.Loading(Note.Empty()))
        runCatching {
            val data = noteDao.getById(value)
            emit(Action.Success(data))
        }.onFailure { exception ->
            emit(Action.Error(Note.Empty(), messsage = exception.message))
        }
    }

    override suspend fun save(value: Note): Flow<Action<Unit>> = flow {
        emit(Action.Loading(Unit))
        runCatching {
            val data = noteDao.save(value)
            emit(Action.Success(data))
        }.onFailure { exception ->
            emit(Action.Error(Unit, messsage = exception.message))
        }
    }

    override suspend fun delete(value: Note): Flow<Action<Unit>> = flow {
        emit(Action.Loading(Unit))
        runCatching {
            noteDao.delete(value)
            emit(Action.Success(Unit))
        }.onFailure { exception ->
            emit(Action.Error(Unit, messsage = exception.message))
        }
    }
    override suspend fun update(value: Note): Flow<Action<Unit>> = flow {
        emit(Action.Loading(Unit))
        runCatching {
            noteDao.update(value)
            emit(Action.Success(Unit))
        }.onFailure { exception ->
            emit(Action.Error(Unit, messsage = exception.message))
        }
    }
}