package ru.tanexc.notegraph.domain.use_cases.note

import kotlinx.coroutines.flow.Flow
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.interfaces.repository.NoteRepository
import ru.tanexc.notegraph.domain.model.note.Note
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(): Flow<Action<Note?>> = repository.create()
}