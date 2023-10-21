package ru.tanexc.notegraph.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.interfaces.dao.NoteDao
import ru.tanexc.notegraph.domain.interfaces.repository.NoteRepository
import ru.tanexc.notegraph.domain.model.note.ImagePiece
import ru.tanexc.notegraph.domain.model.note.Note
import ru.tanexc.notegraph.domain.model.note.TextPiece
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
): NoteRepository {

    override val notesFlow: Flow<List<Note>>
        get() = noteDao.getNotesFlow()

    override fun getByUser(): Flow<Action<List<Note>>> = flow {
        emit(Action.Loading(emptyList()))
        runCatching {
            val data = noteDao.getByUser()
            emit(Action.Success(data))
        }.onFailure { exception ->
            emit(Action.Error(emptyList(), messsage = exception.message))
        }
    }

    override fun getById(value: String): Flow<Action<Note?>> = flow {
        emit(Action.Loading(Note.Empty()))
        runCatching {
            val data = noteDao.getById(value)
            emit(Action.Success(data))
        }.onFailure { exception ->
            emit(Action.Error(Note.Empty(), messsage = exception.message))
        }
    }

    override fun delete(value: Note): Flow<Action<Unit>> = flow {
        emit(Action.Loading(Unit))
        runCatching {
            noteDao.delete(value)
            emit(Action.Success(Unit))
        }.onFailure { exception ->
            emit(Action.Error(Unit, messsage = exception.message))
        }
    }

    override fun create(): Flow<Action<Note?>> = flow {
        emit(Action.Loading(null))
        runCatching {
            val note = noteDao.create()
            emit(Action.Success(note))
        }.onFailure { exception ->
            emit(Action.Error(null, messsage = exception.message))
        }
    }

    override fun save(value: Note): Flow<Action<Unit>> = flow {
        emit(Action.Loading(Unit))
        runCatching {
            noteDao.save(value)
            emit(Action.Success(Unit))
        }.onFailure { exception ->
            emit(Action.Error(Unit, messsage = exception.message))
        }
    }

    override fun updateImagePiece(noteId: String, value: ImagePiece): Flow<Action<Unit>> = flow {
        emit(Action.Loading(Unit))
        runCatching {
            noteDao.updateImagePiece(noteId, value)
            emit(Action.Success(Unit))
        }.onFailure { exception ->
            emit(Action.Error(Unit, messsage = exception.message))
        }
    }

    override fun updateTextPiece(noteId: String, value: TextPiece): Flow<Action<Unit>> = flow {
        emit(Action.Loading(Unit))
        runCatching {
            noteDao.updateTextPiece(noteId, value)
            emit(Action.Success(Unit))
        }.onFailure { exception ->
            emit(Action.Error(Unit, messsage = exception.message))
        }
    }

    override fun deleteImagePiece(noteId: String, value: ImagePiece): Flow<Action<Unit>> = flow {
        emit(Action.Loading(Unit))
        runCatching {
            noteDao.deleteImagePiece(noteId, value)
            emit(Action.Success(Unit))
        }.onFailure { exception ->
            emit(Action.Error(Unit, messsage = exception.message))
        }
    }
    override fun deleteTextPiece(noteId: String, value: TextPiece): Flow<Action<Unit>> = flow {
        emit(Action.Loading(Unit))
        runCatching {
            noteDao.deleteTextPiece(noteId, value)
            emit(Action.Success(Unit))
        }.onFailure { exception ->
            emit(Action.Error(Unit, messsage = exception.message))
        }
    }

    override fun getNoteAsFlow(noteId: String): Flow<Action<Note?>> = flow {
        emit(Action.Loading(null))
        try {
            noteDao.getNoteAsFlow(noteId).collect {
                emit(Action.Success(it))
            }
        } catch (e: Exception) {
            emit(Action.Error(Note.Empty(), messsage = e.message))
        }
    }
}