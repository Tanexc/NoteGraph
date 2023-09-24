package ru.tanexc.notegraph.domain.interfaces.dao

import ru.tanexc.notegraph.domain.model.Note

interface NoteDao {
    suspend fun create(note: Note)

    suspend fun getByUser(uid: String): List<Note>

    suspend fun update(note: Note)
}